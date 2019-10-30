<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据源</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title">数据源</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/etl/dataSourceList.do" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">刷新</a></span>
                <span><a href="${baseUrl}/web/etl/dataSourceEdit.do" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="4%">序号</th>
                    <th width="10%">源名称</th>
                    <th width="10%">类型</th>
                    <th width="10%">IP</th>
                    <th width="10%">端口</th>
                    <th width="10%">数据库名称</th>
                    <th width="10%">用户名</th>
                    <th width="10%">创建时间</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${dataSources}" var="dataSource" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>${dataSource.sourceName}</td>
                        <td>${dataSource.dbType}</td>
                        <td>${dataSource.dbIp}</td>
                        <td>${dataSource.dbPort}</td>
                        <td>${dataSource.dbName}</td>
                        <td>${dataSource.dbUsername}</td>
                        <td>
                            <c:if test="${dataSource.createTime > 0}">
                                <jsp:useBean id="createTime" class="java.util.Date"/>
                                <jsp:setProperty name="createTime" property="time" value="${dataSource.createTime}"/>
                                <fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/web/etl/dataSourceEdit.do?id=${dataSource.id}" target="rightFrame"><img src="${baseUrl}/img/bianji.png">编辑</a></span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div class="fenye">
                <ul class="fy_ul">
                    <li class="fy_li_first">共 ${count} 条记录</li>
                    <c:if test="${currPage == 1}">
                        <li><a href="#">上一页</a></li>
                    </c:if>
                    <c:if test="${currPage > 1}">
                        <li><a href="${baseUrl}/web/etl/dataSourceList.do?page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/etl/dataSourceList.do?page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>