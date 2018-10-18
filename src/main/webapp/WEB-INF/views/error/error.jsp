<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>


<div style="display:none">
<H2>错误：</H2>
<%=exception%>
<H2>错误内容：</H2>
<%
exception.printStackTrace(response.getWriter());
%>
</div>
