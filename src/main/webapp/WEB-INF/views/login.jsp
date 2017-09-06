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

      <form class="form-signin" action="login.action" method="post">
        <h2 class="form-signin-heading">SP后台登陆</h2>
        <label for="inputName" class="sr-only">用户名</label>
        <input type="text" id="inputName" name="user_name"   class="form-control" placeholder="用户名" required autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" name="pwd" class="form-control" placeholder="密码" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登陆</button>
      </form>

    </div> 
</body>
</html>
