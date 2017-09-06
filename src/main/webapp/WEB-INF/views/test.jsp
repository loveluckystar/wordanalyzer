<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>sp后台管理</title>
<%@include file="inc/cssjs.jsp" %>
<link href="css/login.css" rel="stylesheet" type="text/css" />  
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<div class="container">

      <form class="form-signin" action="/compute/newsfortype.action" method="post">
        <h2 class="form-signin-heading">设备文章类型</h2>
        <label for="inputTitle" class="sr-only">标题</label>
        <input type="text" id="inputTitle" name="title"   class="form-control" placeholder="标题" required autofocus>
        <label for="inputContent" class="sr-only">密码</label>
        <input type="password" id="inputContent" name="content" class="form-control" placeholder="密码" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">提交</button>
      </form>
        <textarea rows="" cols=""></textarea>
    </div> 
</body>
</html>
