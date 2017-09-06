<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-sm-3 col-md-2 sidebar">
	<ul class="nav nav-sidebar">
		<li <c:if test="${navtag=='main_overview'}">class="active"</c:if>><a href="${basePath}/manage/info.action?cnum=0">主集群概览 </a></li>
		<li <c:if test="${navtag=='minor_overview'}">class="active"</c:if>><a href="${basePath}/manage/info.action?cnum=1">次集群概览</a></li>
		<li <c:if test="${navtag=='ig_cache_list'}">class="active"</c:if>><a href="${basePath}/manage/cachelist.action">缓存管理</a></li>
		<li <c:if test="${navtag=='ig_query'}">class="active"</c:if>><a href="${basePath}/manage/igquery.action">cache查询</a></li>
	</ul>
	<ul class="nav nav-sidebar">
		<li><a href="">Nav item</a></li>
		<li><a href="">Nav item again</a></li>
		<li><a href="">One more nav</a></li>
		<li><a href="">Another nav item</a></li>
		<li><a href="">More navigation</a></li>
	</ul>
	<ul class="nav nav-sidebar">
		<li><a href="">Nav item again</a></li>
		<li><a href="">One more nav</a></li>
		<li><a href="">Another nav item</a></li>
	</ul>
</div>
