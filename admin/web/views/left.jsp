<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据共享中心</title>
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
            <a target="rightFrame" href="${baseUrl}/web/entity/list.do"><span>数据配置管理</span></a>
        </li>

        <li class="lv1-li">
            <a target="rightFrame" href="${baseUrl}/web/plat/list.do"><span>系统对接管理</span></a>
        </li>

        <li class="lv1-li">
            <span>数据查询<i></i></span>
            <ul class="lv2-ul">
                <li class="lv2-li"><a target="rightFrame" href="${baseUrl}/web/common/queryData.do">数据查询</a></li>
                <li class="lv2-li"><a target="rightFrame" href="${baseUrl}/web/common/attachments.do">附件列表</a></li>
            </ul>
        </li>
        <li class="lv1-li">
            <span>数据传输监控<i></i></span>
            <ul class="lv2-ul">
                <li class="lv2-li"><a target="rightFrame" href="${baseUrl}/web/log/dataTrans.do?type=0">上行数据传输监控</a></li>
                <li class="lv2-li"><a target="rightFrame" href="${baseUrl}/web/log/dataTrans.do?type=1">下行数据传输监控</a></li>
            </ul>
        </li>
        <li class="lv1-li">
            <span>日志管理<i></i></span>
            <ul class="lv2-ul">
                <%--<li class="lv2-li"><a target="rightFrame" href="${baseUrl}/web/log/tfChangeLogs.do">表、字段变更日志</a></li>--%>
                <li class="lv2-li"><a target="rightFrame" href="${baseUrl}/web/log/epChangeLogs.do">权限变更日志</a></li>
            </ul>
        </li>

        <li class="lv1-li">
            <span>ETL管理<i></i></span>
            <ul class="lv2-ul">
                <li class="lv2-li"><a target="rightFrame" href="${baseUrl}/web/etl/dataSourceList.do">数据源</a></li>
                <li class="lv2-li"><a target="rightFrame" href="${baseUrl}/web/etl/projectList.do">任务</a></li>
            </ul>
        </li>

        <li class="lv1-li">
            <a target="rightFrame" href="${baseUrl}/web/monitor/list.do"><span>对接监控</span></a>
        </li>
    </ul>
</div>
</body>
</html>
