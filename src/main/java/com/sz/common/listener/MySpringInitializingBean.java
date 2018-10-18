package com.sz.common.listener;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.sz.common.job.ExampleJob;
import com.sz.common.util.QuartzManager;
import com.sz.example.service.ExampleService;

@Component
public class MySpringInitializingBean implements InitializingBean {
	
	@Resource
	private ExampleService exampleService;

	@Override
	public void afterPropertiesSet() throws Exception {
		
//		System.out.println("#####################GGGGGGGGGGGGGGGGGGGGGGGGGGGGffgggggggg");
//		
//		Map<String, Object> param=new HashMap<>();
//		param.put("exampleService", exampleService);
//		QuartzManager.addJob("testJob", ExampleJob.class, "0/1 * * * * ? ",param);

	}

}
