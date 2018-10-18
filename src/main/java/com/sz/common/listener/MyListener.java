package com.sz.common.listener;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SchedulerException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sz.common.job.ExampleJob;
import com.sz.common.util.QuartzManager;
import com.sz.example.service.ExampleService;

public class MyListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ExampleService exampleService=WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()).getBean(ExampleService.class);
		
//		this.addJob(exampleService);
		
	}
	
	/**
	 * 启动定时任务
	 * @param exampleService
	 */
	private void addJob(ExampleService exampleService) {
		Map<String, Object> param=new HashMap<>();
		param.put("exampleService", exampleService);
		try {
			QuartzManager.addJob("testJob", ExampleJob.class, "0/1 * * * * ? ",param);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}
