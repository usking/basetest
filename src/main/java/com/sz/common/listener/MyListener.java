package com.sz.common.listener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sz.common.job.ExampleJob;
import com.sz.common.util.QuartzManager;
import com.sz.example.service.ExampleService;

public class MyListener implements ServletContextListener {
	
	Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ExampleService exampleService=WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()).getBean(ExampleService.class);
		
//		this.addJob(exampleService);
		
		try {
			ServletContext servletContext=servletContextEvent.getServletContext();
			InputStream inputStream=servletContext.getResourceAsStream("/json/static.json");
			StringBuffer sb=new StringBuffer();
			BufferedReader br=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String temp=br.readLine();
			while(temp!=null){
				sb.append(temp);
				temp=br.readLine();
			}
			br.close();
			//System.out.println(sb.toString());
			logger.info(sb.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
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
