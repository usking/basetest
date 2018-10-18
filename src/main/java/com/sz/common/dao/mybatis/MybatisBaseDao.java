package com.sz.common.dao.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.sz.common.util.CommonUtils;
import com.sz.common.util.PageModel;
import com.sz.common.util.PageUtils;

@Repository
public class MybatisBaseDao {
	
	@Resource
	private SqlSession sqlSession;
	
	public SqlSession getSqlSession(){
		return sqlSession;
	}
	
	public int save(String statement,Object param){
		return sqlSession.insert(statement, param);
	}
	
	public int update(String statement,Object param){
		return sqlSession.update(statement, param);
	}
	
	public int delete(String statement,Object param){
		return sqlSession.delete(statement, param);
	}
	
	public Object getObject(String statement,Object param){
		return sqlSession.selectOne(statement, param);
	}
	
	public int getObjectCount(String statement,Object param){
		return sqlSession.selectOne(statement, param);
	}
	
	public List getObjectList(String statement,Object param){
		return sqlSession.selectList(statement, param);
	}
	
	public PageModel getPageList(String queryStatement,String countStatement,Map<String,Object> param,int pageNo,int pageSize){
		if(param==null){
			param=new HashMap<>();
		}
		param.put("_beginIndex", (pageNo-1)*pageSize);
		param.put("_pageSize", pageSize);
		int maxPageSize=this.getObjectCount(countStatement, param);
		List list=sqlSession.selectList(queryStatement, param);
		return PageUtils.doPage(pageNo, pageSize, maxPageSize, list);
	}
	
	public List<Map<String,Object>> executeSql(String sql,Map<String,Object> param){
		if(param==null){
			param=new HashMap<>();
		}
		param.put("sql", sql);
		List<Map<String,Object>> list=sqlSession.selectList("base.executeSql", param);
		return list;
	}
	
	public <T> List<T> select(String sql,Map<String,Object> param,final Class<T> clazz){
		final List<T> finalList=new ArrayList<>();
		if(param==null){
			param=new HashMap<>();
		}
		param.put("sql", sql);
		sqlSession.select("base.executeSql", param, new ResultHandler() {
			@Override
			public void handleResult(ResultContext context) {
				Map<String,Object> m=(Map)context.getResultObject();
				T t=(T)CommonUtils.map2bean(clazz, m);
				finalList.add(t);
			}
		});
		return finalList;
	}
}
