<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据表</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title">数据表</div>
            <div class="fr operation">
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="4%">序号</th>
                    <th width="10%">表名</th>
                    <th width="16%">中文名称</th>
                    <th width="30%">描述</th>
                    <th width="8%">修改时间</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${busCustomEntities}" var="busCustomEntity" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>${busCustomEntity.tableName}</td>
                        <td class="self_th_td" style="max-width: 80px;">${busCustomEntity.inChinese}</td>
                        <td class="self_th_td">${busCustomEntity.description}</td>
                        <td>
                            <c:if test="${busCustomEntity.updateTime > 0}">
                                <jsp:useBean id="updateTime" class="java.util.Date"/>
                                <jsp:setProperty name="updateTime" property="time" value="${busCustomEntity.updateTime}"/>
                                <fmt:formatDate value="${updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/front/busCustomField/list.do?tableName=${busCustomEntity.tableName}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">查看字段</a></span>
                            <span><a href="${baseUrl}/front/busCustomEntity/dataTemplate.do?tableName=${busCustomEntity.tableName}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">数据上传模板</a></span>
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
                        <li><a href="${baseUrl}/front/busCustomEntity/list.do?page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/front/busCustomEntity/list.do?page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>