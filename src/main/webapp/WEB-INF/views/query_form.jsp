<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>




<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="">
<meta name="author" content="">
<title>缓存查询</title>
<%@include file="inc/cssjs.jsp"%>
<link href="<%=basePath%>/css/dashboard.css" rel="stylesheet">
<script>
	
</script>
</head>

<body>

	<%@include file="inc/adminhead.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<c:set var="navtag" value="ig_query" />
			<%@include file="inc/adminnav.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<h2 class="page-header">查询</h2>
				<form method="post" action="newsfortype.action">
					<div class="form-group">
						<label for="title">标题</label>
						<input class="form-control" id="title" name="title" value="${title}">
					</div>
					<div class="form-group">
						<label for="content">内容</label>
						<textarea id="content" name="content" class="form-control" rows="4">${content}</textarea>
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>

				<h2 class="sub-header">结果</h2>
				<div class="table-responsive col-lg-12">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>汽车</th>
								<th>教育</th>
								<th>娱乐</th>
								<th>财经</th>
								<th>游戏</th>
								<th>房产</th>
								<th>体育</th>
								<th>科技</th>
							</tr>
						</thead>
						<tbody>
						 <c:if test="${not empty resulttype}">
								<tr>
								<td>${resulttype['auto']}</td>
								<td>${resulttype['edu']}</td>
								<td>${resulttype['ent']}</td>
								<td>${resulttype['finance']}</td>
								<td>${resulttype['games']}</td>
								<td>${resulttype['house']}</td>
								<td>${resulttype['sports']}</td>
								<td>${resulttype['tech']}</td>
								</tr>
						 </c:if> 
						</tbody>
					</table>
				</div>
				<h2 class="sub-header">摘要</h2>
				<div class="table-responsive col-lg-12">
					${summarise_info}
				</div>
			</div>
		</div>
	</div>
</body>
</html>
