<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据表字段</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title">数据表字段</div>
            <div class="fr operation">
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="4%">序号</th>
                    <th width="6%">表名</th>
                    <th width="6%">字段名</th>
                    <th width="8%">真实字段名</th>
                    <th width="8%">字段中文名</th>
                    <th width="6%">字段类型</th>
                    <th width="6%">字段长度</th>
                    <th width="6%">字段精度</th>
                    <th width="8%">是否对外暴露</th>
                    <th width="10%">备注</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${busCustomFields}" var="busCustomField" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>${busCustomField.tableName}</td>
                        <td>${busCustomField.fieldName}</td>
                        <td>${busCustomField.realFieldName}</td>
                        <td>${busCustomField.inChinese}</td>
                        <td>
                            <c:if test="${busCustomField.fieldType == 0}">整型</c:if>
                            <c:if test="${busCustomField.fieldType == 1}">字符串</c:if>
                            <c:if test="${busCustomField.fieldType == 2}">时间戳</c:if>
                            <c:if test="${busCustomField.fieldType == 3}">浮点型</c:if>
                        </td>
                        <td>${busCustomField.fieldLength}</td>
                        <td>${busCustomField.precisions}</td>
                        <td>
                            <c:if test="${busCustomField.exposed == 0}">否</c:if>
                            <c:if test="${busCustomField.exposed == 1}">是</c:if>
                        </td>

                        <td>${busCustomField.remarks}</td>
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
                        <li><a href="${baseUrl}/front/busCustomField/list.do?tableName=${tableName}&page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/front/busCustomField/list.do?tableName=${tableName}&page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>