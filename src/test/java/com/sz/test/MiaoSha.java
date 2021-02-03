package com.sz.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sz.common.dao.BaseDao;
import com.sz.common.util.CommonUtils;


/**
 * CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_miaosha`(IN `p_id` int,IN `p_oper` int)
	BEGIN
	DECLARE out_result int;

	IF p_oper=1 THEN

		update trade set current_count = current_count + 1 where id = p_id and current_count + 1 <= max_count;
		select ROW_COUNT() into out_result;
		select out_result;

	ELSE

		update trade set current_count = current_count - 1 where id = p_id and current_count - 1 >= 0;
		select ROW_COUNT() into out_result;
		select out_result;

	END IF;

	END
 * 
 * 
 * 秒杀，通过update语句防止超卖现象发生
 * @author Administrator
 *
 */
@Service
@Transactional
public class MiaoSha implements Runnable {
	
	@Resource
	private BaseDao baseDao;
	
	public MiaoSha() {}
	
	@Override
	public void run() {
		int id=1;//主键id
		try {
			int oper=1;//1增加 0减少
			String sql="{call proc_miaosha(?,?)}";
			List<Object> params=new ArrayList<>();
			params.add(id);
			params.add(oper);
			
			List<Map<String, Object>> list=baseDao.getHibernateDao().queryListBySql(sql, params);
			Map<String,Object> map=list.get(0);
			Integer rs=(Integer)map.get("out_result");
			System.out.println(Thread.currentThread().getName()+"===="+rs+"（"+CommonUtils.dateFormat(new Date(), null)+"）");
			
			Thread.sleep(1000);
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			int oper=0;//1增加 0减少
			String sql="{call proc_miaosha(?,?)}";
			List<Object> params=new ArrayList<>();
			params.add(id);
			params.add(oper);
			baseDao.getHibernateDao().queryListBySql(sql, params);
		}finally {
		}
	}

	public void buy(MiaoSha p) {
		for(int i=1;i<=30;i++) {
			new Thread(p).start();
		}
	}
	
	
	
	public static void main(String[] args) {
		try {
			ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-redis.xml"});
			MiaoSha s=context.getBean("miaoSha",MiaoSha.class);
			s.buy(s);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}

	

}
