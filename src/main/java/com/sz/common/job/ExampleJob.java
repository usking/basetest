package com.sz.common.job;

import java.io.IOException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sz.example.service.ExampleService;

public class ExampleJob implements Job {
	


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			ExampleService exampleService=(ExampleService)context.getJobDetail().getJobDataMap().get("exampleService");
			exampleService.testTask();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
