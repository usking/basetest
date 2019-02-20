package com.sz.common.aop;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sz.common.dao.BaseDao;


@Component
@Aspect
public class TestAop {
	
	public Logger logger = Logger.getLogger("sys");
	
	@Resource
	private BaseDao baseDao;
    
    
    public TestAop(){}
    
    @Before(value="execution(* com.sz.*.controller..*(..))")
    public void testAop(JoinPoint joinpoint) throws Exception {
        String methodName = joinpoint.getSignature().getName();//方法名
        Object target=joinpoint.getTarget();//拦截的实体类
        Class[] parameterTypes = ((MethodSignature)joinpoint.getSignature()).getMethod().getParameterTypes();////拦截的参数类型
        String declaringTypeName = joinpoint.getSignature().getDeclaringTypeName();//包名+类名
        //Object[] params=joinpoint.getArgs();//参数
        
        Class clazz=target.getClass();
        TestAnnotation classAnnotation=(TestAnnotation)clazz.getAnnotation(TestAnnotation.class);
        
        Method method=target.getClass().getMethod(methodName, parameterTypes);
        TestAnnotation methodAnnotation=method.getAnnotation(TestAnnotation.class);
        
        if(classAnnotation!=null || methodAnnotation!=null){
        	String valueStr="";
    		String nameStr="";
        	if(methodAnnotation!=null) {
        		valueStr=methodAnnotation.value();
            	nameStr=methodAnnotation.name();
        	}else if(classAnnotation!=null) {
        		valueStr=classAnnotation.value();
        		nameStr=classAnnotation.name();
        	}
        	System.out.println("######执行了测试Aop"+"（value:"+valueStr+",name:"+nameStr+"）");
        }
    }
    
   
    

}
