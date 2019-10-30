<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>表、字段变更日志</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title">表、字段变更日志</div>
            <div class="fr operation">
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="10%">序号</th>
                    <th width="10%">更新类型</th>
                    <th width="10%">对象ID</th>
                    <th width="10%">更新人</th>
                    <th width="10%">更新时间</th>
                    <th width="10%">更新内容</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${tfChangeLogs}" var="tfChangeLog" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>
                            <c:if test="${tfChangeLog.chgType == 0}">实体</c:if>
                            <c:if test="${tfChangeLog.chgType == 1}">字段</c:if>
                        </td>
                        <td>${tfChangeLog.objId}</td>
                        <td>${tfChangeLog.updateAid}</td>
                        <td>${tfChangeLog.updateTime}
                            <c:if test="${tfChangeLog.updateTime > 0}">
                                <jsp:useBean id="updateTime" class="java.util.Date"/>
                                <jsp:setProperty name="updateTime" property="time" value="${tfChangeLog.updateTime}"/>
                                <fmt:formatDate value="${updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td>${tfChangeLog.content}</td>
                        <td style="text-align: left;">
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
                        <li><a href="${baseUrl}/web/log/tfChangeLogs.do?page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/log/tfChangeLogs.do?page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>