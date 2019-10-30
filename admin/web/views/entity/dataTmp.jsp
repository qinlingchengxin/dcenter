<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据上传模板</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/form.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/menu.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/jquery.extend.js"></script>
    <script type="text/javascript">
        $(function () {
            var url = window.location.protocol + "//" + window.location.host + $("#url").val();
            $("#url").val(url);
        })
    </script>
</head>
<body>
<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first">
            <a href="javascript:history.go(-1);">返回</a><span> > 数据表 > </span><a href="javascript:void(0)">数据上传模板</a>
        </div>
        <div class="second">
            <form id="form">
                <div>
                    <span class="title" style="vertical-align: top;">接口地址</span>
                    <input id="url" type="text" value="${app}/api/syncUps.do" readonly/>
                </div>
                <div>
                    <span class="title" style="vertical-align: top;">请求方式</span>
                    <input type="text" value="POST" readonly/>
                </div>
                <div>
                    <span class="title" style="vertical-align: top;">请求参数</span>
                    <input type="text" value="table_name：表名,platform_code：平台编码,data：如下传输模板" readonly/>
                </div>
                <div>
                    <span class="title" style="vertical-align: top;">传输模板</span>
                    <textarea id="doc-ipt-dataTmp-1" name="dataTmp" style="height: 240px; width: 800px;" readonly>${dataTmp}</textarea>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>