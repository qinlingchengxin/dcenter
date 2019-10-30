<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>实体数据</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/form.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/menu.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/jquery.extend.js"></script>
</head>
<body>
<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first">
            <a href="javascript:history.go(-1);">返回</a><span>></span><a href="javascript:void(0)">实体数据</a>
        </div>
        <div class="second">
            <form id="form">
                <c:forEach items="${fields}" var="field">
                    <c:if test="${field.fieldName != 'SYS__ID'}">
                        <div>
                            <span class="title" style="width: 160px;">${field.inChinese}</span>
                            <input id="${field.fieldName}" type="text" name="${field.fieldName}" value="${entityData[field.realFieldName]}" readonly/>
                        </div>
                    </c:if>
                </c:forEach>
            </form>
        </div>
    </div>
</div>
</body>
</html>