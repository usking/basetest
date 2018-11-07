package com.sz.common.logger;

import java.lang.reflect.Method;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sz.common.dao.BaseDao;
import com.sz.common.util.CommonUtils;

import net.sf.json.JSONObject;


@Component
@Aspect
public class LoggerAop {
	
	public Logger logger = Logger.getLogger("sys");
	
	@Resource
	private BaseDao baseDao;
    
    
    public LoggerAop(){}
    
    
    @Pointcut("execution(* com.sz.*.service..*(..))")
	public void allServiceLogger(){}
	
    /**
     * 向文件写入错误日志
     */
	@AfterThrowing(pointcut="allServiceLogger()",throwing="ex")
	public void afterException(Exception ex){
		StackTraceElement[] stackTraceElement=ex.getStackTrace();
		logger.error(ex);
		for(StackTraceElement error : stackTraceElement){
			logger.error(error.toString());
		}
	}
    
    /**
     * 记录业务日志
     */
    @After(value="execution(* com.sz.*.service..*(..)) || execution(* com.sz.*.controller..*(..))")
    public void saveLog(JoinPoint joinpoint) throws Exception {
        String methodName = joinpoint.getSignature().getName();//方法名
        Object target=joinpoint.getTarget();//拦截的实体类
        Class[] parameterTypes = ((MethodSignature)joinpoint.getSignature()).getMethod().getParameterTypes();////拦截的参数类型
        String declaringTypeName = joinpoint.getSignature().getDeclaringTypeName();//包名+类名
        //Object[] params=joinpoint.getArgs();//参数
        
        Method method=target.getClass().getMethod(methodName, parameterTypes);
        LoggerDoc loggerDoc=method.getAnnotation(LoggerDoc.class);
        if(loggerDoc!=null){
        	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        	String val=loggerDoc.value();
        	Map<String,Object> map=CommonUtils.doParameters(request);
        	JSONObject paramsJson=JSONObject.fromObject(map);
        	this.saveSysLog(request,declaringTypeName, methodName, paramsJson.toString(),val);
        }
    }
    
    public void saveSysLog(HttpServletRequest request,String classname,String methodname,String arguments,String operation){
    	try{
	    	
	    	Object usernameObj=request.getSession().getAttribute("username");
	    	String username="";
	    	if(usernameObj!=null){
	    		username=(String)usernameObj;
	    	}
	    	String ip=CommonUtils.getIpAddr(request);
	    	SysLog sysLog=new SysLog();
	    	sysLog.setId(CommonUtils.getUUID());
	    	sysLog.setUsername(username);
	    	sysLog.setClassname(classname);
	    	sysLog.setMethodname(methodname);
	    	sysLog.setArguments(arguments);
	    	sysLog.setSysid("basetest");
	    	sysLog.setIp(ip);
	    	sysLog.setOperation(operation);
	    	sysLog.setCreateTime(CommonUtils.getCurrentTimestamp());
	    	System.out.println("######"+operation+"######");
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    

}
