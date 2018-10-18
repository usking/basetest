package com.sz.common.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sz.common.dao.hibernate.HibernateBaseDao;
import com.sz.common.dao.mybatis.MybatisBaseDao;
import com.sz.common.dao.spring.SpringBaseDao;

@Repository
public class BaseDao {
	@Resource
	private MybatisBaseDao mybatisBaseDao;
	@Resource
	private SpringBaseDao springBaseDao;
	@Resource
	private HibernateBaseDao hibernateBaseDao;
	
	
	public HibernateBaseDao getHibernateDao() {
		return hibernateBaseDao;
	}
	public MybatisBaseDao getMybatisBaseDao() {
		return mybatisBaseDao;
	}
	public SpringBaseDao getSpringBaseDao() {
		return springBaseDao;
	}
}
