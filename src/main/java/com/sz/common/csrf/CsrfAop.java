package com.sz.common.csrf;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sz.common.constant.Consts;
import com.sz.common.dao.BaseDao;
import com.sz.common.exception.MyException;


@Component
@Aspect
public class CsrfAop {
	
	public Logger logger = Logger.getLogger("sys");
	
	@Resource
	private BaseDao baseDao;
    
    
    public CsrfAop(){}
    
    /**
     * <pre><script type="text/html" style="display: block">
     * 防止csrf攻击
     *     在需要过滤csrf的form里增加
     *     <input name="csrftoken" value="${sessionScope.csrftoken}" />
     *     每次提交表单时需要此属性来验证 
     * </script></pre>
     */
    @Before(value="execution(* com.sz.*.controller..*(..))")
    public void validCsrf(JoinPoint joinpoint) throws Exception {
        String methodName = joinpoint.getSignature().getName();//方法名
        Object target=joinpoint.getTarget();//拦截的实体类
        Class[] parameterTypes = ((MethodSignature)joinpoint.getSignature()).getMethod().getParameterTypes();////拦截的参数类型
        String declaringTypeName = joinpoint.getSignature().getDeclaringTypeName();//包名+类名
        //Object[] params=joinpoint.getArgs();//参数
        
        Method method=target.getClass().getMethod(methodName, parameterTypes);
        CsrfAnnotation csrfAnnotation=method.getAnnotation(CsrfAnnotation.class);
        if(csrfAnnotation!=null){
        	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        	//HttpServletResponse response=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        	String csrftoken=request.getParameter("csrftoken");
    		String csrfsession=(String)request.getSession().getAttribute(Consts.CSRF_TOKEN_NAME);
    		if(StringUtils.isNotBlank(csrftoken) && csrfsession.equals(csrftoken)){
    		}else{
    			//request.getRequestDispatcher("/error/400").forward(request, response);
    			throw new MyException("错误的请求");
    		}
        }
    }
    
   
    

}
