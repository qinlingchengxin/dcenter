<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>系统枚举</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title">系统枚举</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/sys/enumEdit.do?id=0" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="20%">序号</th>
                    <th width="20%">名称</th>
                    <th width="20%">代码</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${busEnums}" var="busEnum" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>${busEnum.code}</td>
                        <td>${busEnum.enumName}</td>
                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/web/sys/enumEdit.do?code=${busEnum.code}" target="rightFrame"><img src="${baseUrl}/img/bianji.png">编辑</a></span>
                            <span><a href="${baseUrl}/web/sys/evList.do?code=${busEnum.code}&enumName=${busEnum.enumName}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">查看枚举值</a></span>
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
                        <li><a href="${baseUrl}/web/sys/enumList.do?page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/sys/enumList.do?page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>