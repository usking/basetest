<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag import="com.sz.common.util.PageModel"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="attributeName" description="requestScope.page对象" required="true" %>
<%
PageModel page666=(PageModel)request.getAttribute(attributeName);
request.setAttribute("page666",page666);
%>

<div class="pageDiv666" style="display:none" pageNo="${page666.pageNo}" maxPageNo="${page666.maxPageNo}" maxPageSize="${page666.maxPageSize}" pageSize="${page666.pageSize}"></div>

<c:choose>
<c:when test="${page.maxPageSize!=0}">

<div class="szPageDiv"> 
<a href="javascript:;" class="firstPage <c:if test="${page666.pageNo eq '1'}">disabled</c:if>" >首页</a> 
<a href="javascript:;" class="prevPage <c:if test="${page666.pageNo eq '1'}">disabled</c:if>"  >上一页</a>
<c:forEach var="pno" begin="${page666.startPageNo}" end="${page666.endPageNo}" step="1">
<c:choose>
<c:when test="${pno==page666.pageNo}">
<a href="javascript:;" class="current">${pno}</a>
</c:when>
<c:otherwise>
<a href="javascript:;" pno="${pno}" class="pno">${pno}</a>
</c:otherwise>
</c:choose>
</c:forEach>
<a href="javascript:;" class="nextPage <c:if test="${page666.pageNo eq page666.maxPageNo}">disabled</c:if>" >下一页</a>
<a href="javascript:;" class="lastPage <c:if test="${page666.pageNo eq page666.maxPageNo}">disabled</c:if>" >末页</a>

<span class="szPageSpan">
共${page666.maxPageSize}条记录 
每页显示<input type="text" style="width: 25px; height: 15px; border: 1px solid #697689; font-size: 11px; vertical-align: middle;" value="${page666.pageSize}" class="inputPageSize" class="inputPageSize" />条
 转到第<input type="text" style="width: 25px; height: 15px; border: 1px solid #697689; font-size: 11px; vertical-align: middle;" value="${page666.pageNo}"  class="inputPageNo"/>/${page666.maxPageNo}页
</span>
</div>

</c:when>
<c:otherwise>

<div class="szPageDiv"> 
<span class="szPageSpan">共0条记录</span>
</div>

</c:otherwise>
</c:choose>
