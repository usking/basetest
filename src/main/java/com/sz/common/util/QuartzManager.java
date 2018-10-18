package com.sz.common.util;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager {
	private static SchedulerFactory sf = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "DEFAULT_JOB_GROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "DEFAULT_TRIGGER_GROUP_NAME";
	
	public static void addJob(String jobName,Class<?> job,String expression,Map<String,Object> params) throws SchedulerException, ParseException{
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail=sched.getJobDetail(jobName, JOB_GROUP_NAME);
		if(jobDetail==null) {
			jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job);
			if(params!=null){
				Set<String> keySet=params.keySet();
				Iterator<String> iterKey =keySet.iterator();
				while(iterKey.hasNext()){
					String key = iterKey.next();
					jobDetail.getJobDataMap().put(key,params.get(key));
				}
			}
			CronTrigger  trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);
			trigger.setCronExpression(expression);
			sched.scheduleJob(jobDetail,trigger);
			if(!sched.isShutdown()){
				sched.start();
			}
		}
	}
	
	public static void addJob(String jobName,String jobGroupName,String triggerName,String triggerGroupName,Class<?> job,String expression,Map<String,Object> params) throws SchedulerException, ParseException{
		Scheduler sched = sf.getScheduler();
		JobDetail jobDetail=sched.getJobDetail(jobName, JOB_GROUP_NAME);
		if(jobDetail!=null) {
			jobDetail = new JobDetail(jobName, jobGroupName, job);
			if(params!=null){
				Set<String> keySet=params.keySet();
				Iterator<String> iterKey =keySet.iterator();
				while(iterKey.hasNext()){
					String key = iterKey.next();
					jobDetail.getJobDataMap().put(key,params.get(key));
				}
			}
			CronTrigger  trigger = new CronTrigger(triggerName, triggerGroupName);
			trigger.setCronExpression(expression);
			sched.scheduleJob(jobDetail,trigger);
			if(!sched.isShutdown()){
				sched.start();
			}
		}
	}
	
	
	public static void modifyJobTime(String jobName,String expression,Map<String,Object> params) throws SchedulerException, ParseException{
		Scheduler sched = sf.getScheduler();
		CronTrigger trigger = (CronTrigger)sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
		if(trigger != null){
			String oldTime = trigger.getCronExpression(); 
			if (!oldTime.equalsIgnoreCase(expression)) {
				JobDetail jobDetail = sched.getJobDetail(jobName,JOB_GROUP_NAME);  
                Class objJobClass = jobDetail.getJobClass();  
                removeJob(jobName);  
                addJob(jobName, objJobClass, expression,params);
			}
			
		}
	}
	
	public static void modifyJobTime(String jobName,String triggerGroupName,String expression) throws SchedulerException, ParseException{
		Scheduler sched = sf.getScheduler();
		CronTrigger trigger = (CronTrigger)sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
		if(trigger != null){
			String oldTime = trigger.getCronExpression(); 
			if (!oldTime.equalsIgnoreCase(expression)) {
				trigger.setCronExpression(expression);
				sched.rescheduleJob(jobName, triggerGroupName, trigger);
			}
			
		}
	}
	
	/**
	public static void modifyJobTime(String triggerName,String triggerGroupName,String expression) throws SchedulerException, ParseException{
		Scheduler sched = sf.getScheduler();
		Trigger trigger = sched.getTrigger(triggerName,triggerGroupName);
		if(trigger != null){
			CronTrigger ct = (CronTrigger)trigger;
			ct.setCronExpression(expression);
			sched.resumeTrigger(triggerName,triggerGroupName);
		}
	}
	**/
	
	
	public static void removeJob(String jobName) throws SchedulerException{
		Scheduler sched = sf.getScheduler();
		sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);
		sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);
		sched.deleteJob(jobName, JOB_GROUP_NAME);
	}
	
	public static void removeJob(String jobName,String jobGroupName,String triggerName,String triggerGroupName) throws SchedulerException{
		Scheduler sched = sf.getScheduler();
		sched.pauseTrigger(triggerName, triggerGroupName);
		sched.unscheduleJob(triggerName, triggerGroupName);
		sched.deleteJob(jobName, jobGroupName);
	}
	
	
	/** 
	 * 启动所有定时任务   
     */  
	public static void startJobs() throws SchedulerException {  
    	Scheduler sched = sf.getScheduler();  
    	sched.start();  
    }  
  
    /** 
     * 关闭所有定时任务  
     * @throws SchedulerException 
     */ 
    public static void shutdownJobs() throws SchedulerException {  
		Scheduler sched = sf.getScheduler();  
		if (!sched.isShutdown()) {  
		    sched.shutdown();  
		}  
    }
}
