<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>权限变更日志</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title">权限变更日志</div>
            <div class="fr operation">
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="4%">序号</th>
                    <th width="10%">平台编码</th>
                    <th width="10%">平台名称</th>
                    <th width="15%">表名</th>
                    <th width="8%">变更类型</th>
                    <th width="15%">变更人</th>
                    <th width="15%">变更时间</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${epChangeLogs}" var="epChangeLog" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>${epChangeLog.platformCode}</td>
                        <td>${epChangeLog.platformName}</td>
                        <td>${epChangeLog.tableName}</td>
                        <td>
                            <c:if test="${epChangeLog.chgType == 0}">新增</c:if>
                            <c:if test="${epChangeLog.chgType == 1}">取消</c:if>
                        </td>
                        <td>${epChangeLog.updateAid}</td>
                        <td>
                            <c:if test="${epChangeLog.updateTime > 0}">
                                <jsp:useBean id="updateTime" class="java.util.Date"/>
                                <jsp:setProperty name="updateTime" property="time" value="${epChangeLog.updateTime}"/>
                                <fmt:formatDate value="${updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
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
                        <li><a href="${baseUrl}/web/log/epChangeLogs.do?page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/log/epChangeLogs.do?page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>