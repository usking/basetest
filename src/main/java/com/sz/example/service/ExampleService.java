package com.sz.example.service;

import java.io.IOException;
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
import com.sz.common.logger.LoggerDoc;
import com.sz.common.util.CommonUtils;
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
	
	@LoggerDoc("新增或修改item")
	public void mergeItem(Item item) throws Exception{
		String id=item.getId();
		if(StringUtils.isBlank(id)){//新增
			item.setId(CommonUtils.getUUID().toUpperCase());
			baseDao.getSpringBaseDao().insert(item);
		}else{//修改
			baseDao.getSpringBaseDao().update(item);
		}
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
