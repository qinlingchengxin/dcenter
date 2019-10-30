<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据检索</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript">
        function searchData() {
            var tableName = document.getElementById("tableName").value;
            window.location = "${baseUrl}/front/queryData.do?tableName=" + tableName;
        }
    </script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title">数据检索</div>
            <div class="fr operation">
            </div>
        </div>

        <div class="second">
            <form>
                <span>数据表：</span>
                <select id="tableName">
                    <c:forEach items="${entities}" var="entity">
                        <option value="${entity.TABLE_NAME}"
                                <c:if test="${entity.TABLE_NAME == tableName}">selected="selected" </c:if> >${entity.IN_CHINESE}</option>
                    </c:forEach>
                </select>
                <input type="button" onclick="searchData();" value="搜索"/>
            </form>
        </div>

        <div class="third">
            <table>
                <tr>
                    <th width="8%">序号</th>
                    <c:forEach items="${fields}" var="field" varStatus="vs">
                        <c:if test="${field.fieldName != 'SYS__ID'}">
                            <th>${field.inChinese}</th>
                        </c:if>
                    </c:forEach>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${entityDataList}" var="entityData" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <c:forEach items="${entityData}" var="data">
                            <c:if test="${data.key != 'SYS__ID'}">
                                <td>
                                    <c:out value="${data.value}"/>
                                </td>
                            </c:if>
                        </c:forEach>

                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/front/viewData.do?tableName=${tableName}&sysId=${entityData.SYS__ID}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">查看</a></span>
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
                        <li><a href="${baseUrl}/front/queryData.do?tableName=${tableName}$page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/front/queryData.do?tableName=${tableName}$page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>