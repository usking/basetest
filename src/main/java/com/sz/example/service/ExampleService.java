package com.sz.example.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.sz.common.dao.BaseDao;
import com.sz.common.datatables.DataTables;
import com.sz.common.datatables.DataTablesParameter;
import com.sz.common.exception.MyRuntimeException;
import com.sz.common.logger.LoggerDoc;
import com.sz.common.util.CommonUtils;
import com.sz.common.util.ExcelUtils;
import com.sz.common.util.PageModel;
import com.sz.example.dao.mapper.ItemMapper;
import com.sz.example.dao.mapper.ItemTKMapper;
import com.sz.example.pojo.Item;
import com.sz.example.websocket.MessageBean;
import com.sz.example.websocket.SystemWebSocketHandler;

@Service
@Transactional
public class ExampleService {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private ItemMapper itemMapper;
	@Resource
	private ItemTKMapper itemTKMapper;
	@Resource
	private SystemWebSocketHandler systemWebSocketHandler;
	
	public DataTables dataTablesPageItem(DataTablesParameter dataTablesParameter,String title){
		Map<String,Object> param=new HashMap<String,Object>();
		if(title!=null && !title.isEmpty()){
			param.put("title", "%"+title+"%");
		}
		param.put("sortCol", dataTablesParameter.getSortColumn());
		param.put("order", dataTablesParameter.getOrder());
		PageModel pageModel=baseDao.getMybatisBaseDao().getPageList("Item.getPageItem", "Item.getPageItemCount", param, dataTablesParameter.getPageNo(), dataTablesParameter.getLength());
		List<Item> itemList=pageModel.getList();
		DataTables dt=dataTablesParameter.getDataTables(Integer.parseInt(pageModel.getMaxPageSize()), itemList);
		return dt;
	}
	
	public Item getItemInfo(String id){
		//使用普通mapper
//		Item item=itemMapper.selectByPrimaryKey(id);
		//使用tk通用mapper
		Item item=itemTKMapper.selectByPrimaryKey(id);
		
		item.setTitle(HtmlUtils.htmlUnescape(item.getTitle()));
		item.setMemo(HtmlUtils.htmlUnescape(item.getMemo()));
		
		return item;
	}
	
	@LoggerDoc("新增或修改item-Service")
	public void mergeItem(Item item) throws Exception{
		String id=item.getId();
		if(StringUtils.isBlank(id)){//新增
			item.setId(CommonUtils.getUUID().toUpperCase());
			baseDao.getSpringBaseDao().insert(item);
		}else{//修改
			baseDao.getSpringBaseDao().update(item);
		}
		//throw new MyRuntimeException("自定义运行的异常");
	}
	
	public void deleteItem(String id){
		baseDao.getSpringBaseDao().deleteById(Item.class, id);
	}
	
	public void testTask() throws IOException {
		String message="测试定时任务##########"+CommonUtils.dateFormat(new Date(), null);
		System.out.println(message);
		MessageBean messageBean=new MessageBean();
		messageBean.setMessage(message);
		messageBean.setTime(CommonUtils.dateFormat(new Date(), null));
		systemWebSocketHandler.sendMessage(messageBean);
	}
	
	
	public String readExcel(InputStream inputStream) throws IOException {
		StringBuffer sb=new StringBuffer();
		String[][] excel=ExcelUtils.readExcel(inputStream, 0, 0, 0);
		for(int i=0;i<excel.length;i++) {
			String col1=excel[i][0];
			String col2=excel[i][1];
			String col3=excel[i][2];
			String col4=excel[i][3];
			String row=col1+"|"+col2+"|"+col3+"|"+col4;
			sb.append(row+"<br />");
			System.out.println(row);
		}
		return sb.toString();
	}
	
	public void writeExcel(OutputStream out) throws IOException {
		String sheetTitle="测试sheet";
		String[] headers= {"标题a","标题b","标题c","我是标题"};
		
		List<String> columnList=new ArrayList<>();
		columnList.add("数据1a");
		columnList.add("35");
		columnList.add("66.8");
		columnList.add("2018-01-30");
		
		List<List<String>> rowList=new ArrayList<>();
		for(int i=0;i<5;i++) {
			rowList.add(columnList);
		}
		ExcelUtils.writeExcel(out, sheetTitle, headers, rowList);
	}

	public static void main(String[] args) {
		try{
			ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml"});
			ExampleService s=(ExampleService)context.getBean("exampleService");
			System.out.println("main方法执行完成");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
