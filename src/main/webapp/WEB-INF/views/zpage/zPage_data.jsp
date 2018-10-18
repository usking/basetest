<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tag.jsp"%>

<table class="stable" style="width:100%;">
<tr>
<th>编号</th><th>标题</th><th>备注</th>
</tr>
<c:forEach var="entity" items="${goodsPage.list}">
<tr>
<td>${entity.id}</td><td>${entity.title}</td><td>${entity.memo}</td>
</tr>
</c:forEach>
</table>
<z:zpager attributeName="goodsPage"></z:zpager>
<div>
共<span id="totalCounter"></span>条记录，当前第<span id="currentPager"></span>/<span id="totalPager"></span>页
</div>
