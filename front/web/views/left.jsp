<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据共享中心-前置机</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/menu.js"></script>
</head>
<body>
<div class="content_left">
    <ul class="lv1-ul">
        <li class="lv1-li">
            <span>数据配置管理<i class="i_active"></i></span>
            <ul class="lv2-ul" style="display: block;">
                <li class="lv2-li"><a class="lv2-li_a_active" target="rightFrame" href="${baseUrl}/front/busCustomEntity/list.do">数据表</a></li>
            </ul>
        </li>
    </ul>
</div>
</body>
</html>
