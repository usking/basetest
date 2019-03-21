package com.sz.common.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * http://127.0.0.1:8983/solr
 * 需要jdk1.8以上
 * windows启动和关闭进入solr-6.6.5\bin文件夹执行命令
 * 启动solr：solr.cmd start
 * 关闭solr：solr.cmd stop -all
 * 创建solr命令：solr create -c myname
 * 修改字段属性：solr-6.6.5\server\solr\mycore1\conf\managed-schema，将strings修改成string，重启
 * 
 * IK分词器：
 * 添加ik-analyzer-solr5-5.x.jar到solr-6.6.5\server\solr-webapp\webapp\WEB-INF\lib
 * solr-6.6.5\server\solr\mycore1\conf\solrconfig.xml添加配置<lib dir="./lib" regex=".*\.jar"/>
 * managed-schema添加如下配置，然后修改字段属性 type="text_ik"
 * <fieldType name="text_ik" class="solr.TextField">
	<analyzer type="index" useSmart="false" >
	  <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory"/>
	  <filter class="solr.SynonymGraphFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/>
	</analyzer>
	<analyzer type="query" useSmart="true" >
	   <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory"/>
	   <filter class="solr.SynonymGraphFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/> 
	</analyzer>
   </fieldType>
 * @author s
 *
 */
@Component
public class SolrHandler {
	@Resource
	private HttpSolrClient httpSolrClient;
	
	public SolrHandler() {}
	
	public void addIndex(IndexEntity indexEntity) throws IOException, SolrServerException {
		httpSolrClient.addBean(indexEntity);
		httpSolrClient.commit();
	}
	
	public List<IndexEntity> search(String searchStr,int pageNo,int pageSize) throws SolrServerException, IOException {
		String queryStr="*:*";
		if(searchStr!=null && !"".equals(searchStr)) {
			queryStr="title:"+searchStr+" OR content:"+searchStr;
		}
		int start=(pageNo-1)*pageSize;
		SolrQuery query=new SolrQuery();
		query.setQuery(queryStr);
		query.setStart(start);
		query.setRows(pageSize);
		query.setHighlightFragsize(50);
		query.setHighlight(true);
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		query.addHighlightField("title");
		query.addHighlightField("content");
		
		List<IndexEntity> indexList=new ArrayList<IndexEntity>();
		
		QueryResponse rsp = httpSolrClient.query(query);
		SolrDocumentList solrDocumentList=rsp.getResults();
		Map<String, Map<String, List<String>>> highLightMap=rsp.getHighlighting();
		for(int i=0;i<solrDocumentList.size();i++){
			SolrDocument doc=solrDocumentList.get(i);
			String id=(String)doc.get("id");
			String title=(String)doc.get("title");
			String content=(String)doc.get("content");
			
			List<String> titleHightLight=highLightMap.get(id).get("title");
			List<String> contentsHightLight=highLightMap.get(id).get("content");
			if(titleHightLight!=null && titleHightLight.size()>0){
			    title=titleHightLight.get(0);
            }
			if(contentsHightLight!=null && contentsHightLight.size()>0){
			    content=contentsHightLight.get(0);
			}
			
			IndexEntity indexEntity=new IndexEntity();
			indexEntity.setId(id);
			indexEntity.setTitle(title);
			indexEntity.setContent(content);
			indexList.add(indexEntity);
		}
		return indexList;
	}
	
	public long searchTotalCount(String searchStr) throws Exception{
	    SolrQuery query = new SolrQuery();
        String sql="title:"+searchStr+" OR content:"+searchStr;
        if(StringUtils.isBlank(searchStr)) {
        	sql="*:*";
        }
        query.setQuery(sql);
        QueryResponse res = httpSolrClient.query(query);
        return res.getResults().getNumFound();
	}
	
	public void deleteById(String id) throws Exception{
		httpSolrClient.deleteById(id);
		httpSolrClient.commit();
    }

	public void deleteAll() throws Exception{
		httpSolrClient.deleteByQuery("*:*");
		httpSolrClient.commit();
	}
	
	public void createIndex() throws Exception {
		deleteAll();
		List<IndexEntity> indexList=new ArrayList<IndexEntity>();
		for(int i=1;i<=5;i++) {
			IndexEntity indexEntity=new IndexEntity();
			indexEntity.setId(i+"");
			indexEntity.setTitle("标题"+i);
			indexEntity.setContent("内容abc"+i);
			addIndex(indexEntity);
		}
	}
	
	public static void main(String[] args) {
		try {
			ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml"});
			SolrHandler solrHandler=context.getBean("solrHandler", SolrHandler.class);
//			solrHandler.createIndex();
//			List<IndexEntity> list=solrHandler.search("*1*",1,10);
//			for(IndexEntity t : list) {
//				System.out.println(t.getId()+"|"+t.getTitle()+"|"+t.getContent());
//			}
			long count=solrHandler.searchTotalCount(null);
			System.out.println(count);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}
}
