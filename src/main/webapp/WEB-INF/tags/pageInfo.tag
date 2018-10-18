<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="attributeName" description="requestScope.page对象" required="true" %>
<%@ tag import="com.sz.common.util.PageModel"%>
<%
PageModel page666=(PageModel)request.getAttribute(attributeName);
request.setAttribute("page666",page666);
%>

<div class="pageDiv666" style="display:none" pageUrl="${pageUrl}" pageNo="${page666.pageNo}" maxPageNo="${page666.maxPageNo}" maxPageSize="${page666.maxPageSize}" pageSize="${page666.pageSize}"></div>

