package com.sz.common.dao.hibernate;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sz.common.util.PageModel;
import com.sz.common.util.PageUtils;


@Repository
public class HibernateBaseDao {
	
	@Resource
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	public <T> void deleteById(Class<T> clazz,Serializable id) {
		getSession().delete(findById(clazz,id));
	}

	public <T> List<T> findAll(Class<T> clazz,String orderBy,String sort) {
		Criteria criteria=getSession().createCriteria(clazz);
		if("asc".equals(sort)){
			criteria.addOrder(Order.asc(orderBy));
		}else if("desc".equals(sort)){
			criteria.addOrder(Order.desc(orderBy));
		}
		return criteria.list();
	}

	public <T> List<T> findByExample(T t) {
		return getSession().createCriteria(t.getClass()).add(Example.create(t)).list();
	}

	public <T> T findById(Class<T> clazz,Serializable id) {
		T t=(T)getSession().get(clazz, id);
		return t;
	}

	public <T> void merge(T t) {
		getSession().merge(t);
	}

	public <T> Serializable save(T t) {
		return getSession().save(t);
	}

	public <T> void update(T t) {
		getSession().update(t);
	}
	

	public int countByCriteria(Criteria criteria){
		criteria.setProjection(Projections.rowCount());
		return Integer.parseInt(criteria.uniqueResult().toString());
	}
	
	public PageModel queryPageByCriteria(Criteria criteria,int pageNo,int pageSize){
		int maxPageSize=this.countByCriteria(criteria);
		criteria.setProjection(null);
		criteria.setFirstResult((pageNo-1)*pageSize);
		criteria.setMaxResults(pageSize);
		List list=criteria.list();
		PageModel page=PageUtils.doPage(pageNo, pageSize, maxPageSize, list);
		return page;
	}
	
	public List queryListByHql(String hql,List<Object> params){
		Query query=getSession().createQuery(hql);
		if(params!=null){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		List list=query.list();
		return list;
	}
	
	public List queryListByHql(String hql,Map<String,Object> params){
		Query query=getSession().createQuery(hql);
		if(params!=null){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		List list=query.list();
		return list;
	}
	
	
	public List<Map<String,Object>> queryListBySql(String sql,List<Object> params){
		Query query=getSession().createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if(params!=null){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		List<Map<String,Object>> list=query.list();
		return list;
	}
	
	
	private <T> List<T> queryListSql(String sql,Integer pageNo,Integer pageSize,Class<T> clazz,List<Object> params){
		Query query=getSession().createSQLQuery(sql);
//		query.setResultTransformer(new ColumnToBean(clazz));
		query.setResultTransformer(Transformers.aliasToBean(clazz));
		if(params!=null){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		if(pageNo!=null && pageSize!=null){
			query.setFirstResult((pageNo-1)*pageSize);
			query.setMaxResults(pageSize);
		}
		List<T> list=query.list();
		return list;
	}
	
	public <T> PageModel queryPageBySql(String sql,int pageNo,int pageSize,Class<T> clazz,List<Object> params){
		String countSql="select count(*) from ("+sql+") tmp0";
		PageModel page=this.queryPageBySql(sql, countSql, pageNo, pageSize, clazz, params);
		return page;
	}
	
	public <T> PageModel queryPageBySql(String sql,String countSql,int pageNo,int pageSize,Class<T> clazz,List<Object> params){
		int maxPageSize=this.countBySql(countSql, params);
		List<T> list=this.queryListSql(sql, pageNo, pageSize, clazz, params);
		PageModel page=PageUtils.doPage(pageNo, pageSize, maxPageSize, list);
		return page;
	}
	
	public <T> List<T> queryListBySql(String sql,Class<T> clazz,List<Object> params) {
		List<T> list=this.queryListSql(sql, null, null, clazz, params);
		return list;
	}
	
	public int countBySql(String sql,List<Object> params){
		Query query=getSession().createSQLQuery(sql);
		if(params!=null){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	public Object queryObjectByHql(String hql,List<Object> params){
		Query query=getSession().createQuery(hql);
		if(params!=null){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		return query.uniqueResult();
	}
	
	public int executeHql(String hql,List<Object> params){
		Query query=getSession().createQuery(hql);
		if(params!=null){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		return query.executeUpdate();
	}
	
	public int executeSql(String sql,List<Object> params){
		Query query=getSession().createSQLQuery(sql);
		if(params!=null){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		return query.executeUpdate();
	}
}

