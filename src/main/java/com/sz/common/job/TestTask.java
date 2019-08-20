package com.sz.common.job;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sz.common.util.CommonUtils;
import com.sz.example.service.ExampleService;

@Component
public class TestTask {
	
	@Resource
	private ExampleService exampleService;

	//@Scheduled(cron="0/1 * * * * ?")
	public void testTask() throws IOException {
		String time=CommonUtils.dateFormat(new Date(), null);
		System.out.println("########测试定时任务"+time+"===================================");
		exampleService.testTask();
	}
}
