<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" class="opacity">
  <head>
    <meta charset="utf-8">
    <title>SuperDiamond 配置管理服务器</title>
      <link href='<c:url value="/resources/css/bootstrap/css/bootstrap.min.css" />' rel="stylesheet" >
      <link href='<c:url value="/resources/css/bootstrap/css/font-awesome.min.css" />' rel="stylesheet" >
      <link href='<c:url value="/resources/css/bootstrap/css/login.css" />' rel="stylesheet" >
      <script type="text/javascript" src='<c:url value="/resources/js/jquery.min.js" />'></script>
      <script type="text/javascript" src='<c:url value="/resources/js/jquery.cookie.js" />'></script>
      <script type="text/javascript" src='<c:url value="/resources/js/jquery.base64.js" />'></script>
      <script type="text/javascript" src='<c:url value="/resources/js/bootstrap3-validation.js" />'></script>
</head>
<body class="login2">
<!-- Login Screen -->
<div class="login-wrapper">
    <a href="#"><img src="/resources/css/bootstrap/img/logo-login@2x.png"></a>
    <form id="loginForm" class="form-signin"  action='<c:url value="/login" />' method="post" autocomplete="off">
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon">
                    <i class="fa fa-envelope-o fa-fw"></i>
                </span>
                <input id="userCode" name="userCode" value="${sessionScope.userCode}" check-type="required" class="form-control" placeholder="用户名" autofocus="">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
                <input type="password" placeholder="密码" name="password" id="password" check-type="required" class="form-control">
            </div>
        </div>
        <div class="checkbox pull-left">
            <label>
                <input type="checkbox" value="remember-me"><span>Remember me</span>
            </label>
        </div>
        <div class="clearfix">
        <button class="btn btn-lg btn-primary btn-block" onclick="doSubmit()" type="button">Sign in</button>
        </div>
    </form>
</div>
<!-- End Login Screen -->
</body>
<script language="JavaScript">
    $(document).ready(function () {
        $("#loginForm").validation();
        var user=$.cookie('userCode');
        var pwd=$.cookie('password');
        if(user!=""&&user!=undefined){
            $("[type='checkbox']").attr("checked",true);
            $("#userCode").val(user);
            $("#password").val($.base64.decode(pwd));
        }else{
            $("[type='checkbox']").attr("checked",false);
        }
    });

    function doSubmit(){
        if ($("#loginForm").valid(this,'内容出错')==false){
            return;
        }
        var check=$("[type='checkbox']").is(':checked');
        if(check){
            var user=$("#userCode").val();
            var pwd=$("#password").val();
            $.cookie('userCode', user, { expires: 3 });
            $.cookie('password', $.base64.encode(pwd), { expires: 3 });
        }else{
            $.removeCookie('userCode');
            $.removeCookie('password');
        }
        $("#loginForm")[0].submit();
    }
</script>
</html>