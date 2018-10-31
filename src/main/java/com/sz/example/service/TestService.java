package com.sz.example.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sz.common.dao.BaseDao;
import com.sz.common.exception.MyRuntimeException;
import com.sz.common.test.Goods;
import com.sz.common.test.GoodsDB;
import com.sz.common.util.CommonUtils;
import com.sz.common.util.PageModel;
import com.sz.common.util.PageUtils;
import com.sz.example.dao.mapper.ItemMapper;
import com.sz.example.pojo.Item;

@Service
@Transactional
public class TestService {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private ItemMapper itemMapper;
	
	public PageModel testPageGoods(int pageNo,int pageSize,String title){
		List<Goods> allGoodsList=GoodsDB.getAllGoods(title);
		List<Goods> goodsList=new ArrayList<Goods>();
		int maxPageSize=allGoodsList.size();
		int beginIndex=(pageNo-1)*pageSize;
		int endIndex=beginIndex+pageSize-1;
		if(endIndex>maxPageSize-1){
			endIndex=maxPageSize-1;
		}
		for(int i=beginIndex;i<=endIndex;i++){
			Goods goods=allGoodsList.get(i);
			goodsList.add(goods);
		}
		PageModel page=PageUtils.doPage(pageNo, pageSize, maxPageSize, goodsList);
		return page;
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public void testSpring01() throws Exception{
		Item item=baseDao.getSpringBaseDao().findById(Item.class, "89d1586c342c462ca60f4aca4f05a5ee");
		printItem(item);
	}
	
	public void testMybatis01(){
		int pageNo=1;
		int pageSize=5;
		Map<String,Object> param=new HashMap<>();
		List<String> idList=new ArrayList<>();
		idList.add("4E123EB1958B4DCEB57A34E0077258A5");
		idList.add("8a0a48c01723424e96ed41ce98b390ca");
		idList.add("cdfb53c6852847a088376f9de01a5b0a");
		param.put("idList", idList);
		param.put("title", "%标题%");
		PageModel page=baseDao.getMybatisBaseDao().getPageList("Item.getPageItem", "Item.getPageItemCount", param, pageNo, pageSize);
		printItem(page.getList());
	}
	
	public void testHibernate01(){
		int pageNo=1;
		int pageSize=6;
		String sql="select id,title,memo,money from item where 1=1";
		sql+=" and title like ? ";
		String countSql="select count(*) from ("+sql+") t";
		sql+=" order by money";
		List<Object> params=new ArrayList<>();
		params.add("%标题%");
		PageModel page=baseDao.getHibernateDao().queryPageBySql(sql, countSql, pageNo, pageSize, Item.class, params);
		printItem(page.getList());
	}
	
	public void testHibernate02(){
		String sql="select id,title as str1,memo,money from item where 1=1";
		sql+=" and title like ? ";
		List<Object> params=new ArrayList<>();
		params.add("%标题%");
		List<Item> list=baseDao.getHibernateDao().queryListBySql(sql, Item.class, params);
		this.printItem(list);
	}
	
	public void testHibernate03() {
		List<Object> param=null;
		String hql="from Item";
		List<Item> list=baseDao.getHibernateDao().queryListByHql(hql, param);
		this.printItem(list);
	}
	
	public void testHibernate04() {
		String sql="select id,title as str1,memo,money from item where 1=1";
		sql+=" and title like :title ";
		String countSql="select count(*) from("+sql+") t";
		Map<String,Object> params=new HashMap<>();
		params.put("title", "%的%");
//		List<Item> itemList=baseDao.getHibernateDao().queryListBySql(sql, Item.class, params);
//		List<Map<String,Object>> list=baseDao.getHibernateDao().queryListBySql(sql, params);
//		PageModel page=baseDao.getHibernateDao().queryPageBySql(sql, countSql, 1, 10, params);
		PageModel page=baseDao.getHibernateDao().queryPageBySql(sql, countSql, 1, 10,Item.class, params);
		this.printItem(page.getList());
	}
	
	private Item newItem(){
		Item item=new Item();
		item.setId(CommonUtils.getUUID());
		item.setTitle("测试标题"+CommonUtils.getRandomStr(5, 1));
		item.setMemo("测试beizhu"+CommonUtils.getRandomStr(5, 1));
		item.setMoney(new BigDecimal(CommonUtils.getRandomStr(4, 3)));
		return item;
	}
	
	
	public void testExecuteSql1(){
		String sql="select id,title,memo,money from item where id='48c6b71cf38c47ffb9a981545425dd3b'";
//		String sql="select count(*) as c from item";
//		String sql="update item set memo='33445' where id='48c6b71cf38c47ffb9a981545425dd3b'";
		
		List<Map<String,Object>> list=baseDao.getMybatisBaseDao().executeSql(sql,null);//执行update时返回的list为空
		Map<String,Object> map=list.get(0);
		Item item=(Item)CommonUtils.map2bean(Item.class, map);
		this.printItem(item);
		
//		baseDao.getMybatisBaseDao().executeSql(sql,null);
	}
	
	public void testExecuteSql2(){
		String sql="select id,title,memo,money from item where id=#{id}";
		Map<String,Object> param=new HashMap<>();
		param.put("id", "48c6b71cf38c47ffb9a981545425dd3b");
		
		List<Map<String,Object>> list=baseDao.getMybatisBaseDao().executeSql(sql,param);
		Map<String,Object> map=list.get(0);
		Item item=(Item)CommonUtils.map2bean(Item.class, map);
		this.printItem(item);
	}
	
	public void testExecuteSql3(){
		String sql="select id,title,memo,money from item where id=#{id}";
		Map<String,Object> param=new HashMap<>();
		param.put("id", "48c6b71cf38c47ffb9a981545425dd3b");
		
		List<Item> itemList=baseDao.getMybatisBaseDao().select(sql, param,Item.class);
		this.printItem(itemList);
	}
	
	private void printItem(Item item){
		System.out.println("Item实体######"+item.getId()+"|"+item.getTitle()+"|"+item.getMemo()+"|"+item.getMoney()+"|"+item.getStr1());
	}
	private void printItem(List<Item> itemList){
		for(Item item : itemList){
			printItem(item);
		}
	}
	
	private void printItemMap(Map<String,Object> map) {
		System.out.println("ItemMap######"+map.get("id")+"|"+map.get("title")+"|"+map.get("memo")+"|"+map.get("money")+"|"+map.get("str1"));
	}
	
	private void printItemMap(List<Map<String,Object>> list) {
		for(Map<String,Object> map : list) {
			printItemMap(map);
		}
	}

	public static void main(String[] args) {
		try{
			ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-redis.xml"});
			TestService s=(TestService)context.getBean("testService");
			//s.testSpring01();
			//s.testMybatis01();
			//s.testHibernate01();
			//s.testExecuteSql1();
			//s.testExecuteSql2();
			//s.testExecuteSql3();
			//s.testHibernate02();
			//s.testHibernate03();
			s.testHibernate04();
			System.out.println("main方法执行完成");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public void testIndex() {
		System.out.println("######执行了testIndex######");
		throw new MyRuntimeException("自定义异常 哈哈哈哈");
	}

}
