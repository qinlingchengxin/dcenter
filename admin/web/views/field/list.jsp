<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>字段</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript">
        function removeField(tableName, fieldName) {
            $.ajax({
                url: "${baseUrl}/web/field/remove.do?tableName=" + tableName + "&fieldName=" + fieldName,
                type: "GET",
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#tr_" + fieldName).remove();
                        alert('删除成功');
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }

        function searchData() {
            var fieldName = document.getElementById("s_field_name").value;
            window.location = "${baseUrl}/web/field/list.do?tableName=${tableName}&fieldName=" + fieldName;
        }
    </script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title"><a class="title" href="javascript:history.go(-1);">返回</a> > 数据表 > 字段</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/field/list.do?tableName=${tableName}&fieldName=${fieldName}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">刷新</a></span>
                <span><a href="${baseUrl}/web/field/edit.do?tableName=${tableName}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>

        <div class="second">
            <form>
                <span>字段名：</span>
                <input style="width: 120px; height: 26px;background-color: #ffffff;color:#000;border: 1px solid #EAEBE8;cursor: auto;" type="text" id="s_field_name" value="${fieldName}"/>
                <input type="button" onclick="searchData();" value="搜索"/>
            </form>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="6%">序号</th>
                    <th width="8%">表名</th>
                    <th width="8%">字段名</th>
                    <th width="8%">字段中文名</th>
                    <th width="6%">字段类型</th>
                    <th width="6%">字段长度</th>
                    <th width="6%">字段精度</th>
                    <th width="6%">是否必填</th>
                    <th width="8%">值域</th>
                    <th width="6%">是否去重</th>
                    <th width="6%">是否暴露</th>
                    <th width="10%">修改时间</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${busCustomFields}" var="busCustomField" varStatus="vs">
                    <tr id="tr_${busCustomField.fieldName}">
                        <td>${vs.index + 1}</td>
                        <td>${busCustomField.tableName}</td>
                        <td>${busCustomField.fieldName}</td>
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
                            <c:if test="${busCustomField.required == 0}">否</c:if>
                            <c:if test="${busCustomField.required == 1}">是</c:if>
                        </td>
                        <td class="self_th_td"> ${busCustomField.valueField}</td>

                        <td>
                            <c:if test="${busCustomField.uniqueKey == 0}">否</c:if>
                            <c:if test="${busCustomField.uniqueKey == 1}">是</c:if>
                        </td>
                        <td>
                            <c:if test="${busCustomField.exposed == 0}">否</c:if>

                            <c:if test="${busCustomField.exposed == 1}">
                                <c:if test="${busCustomField.fieldName != 'SYS__ID' && busCustomField.fieldName != 'SYS__PLATFORM_CODE' && busCustomField.fieldName != 'SYS__CREATE_TIME' }">
                                    是
                                </c:if>

                                <c:if test="${busCustomField.fieldName == 'SYS__ID' || busCustomField.fieldName == 'SYS__PLATFORM_CODE' || busCustomField.fieldName == 'SYS__CREATE_TIME' }">
                                    否
                                </c:if>
                            </c:if>
                        </td>

                        <td>
                            <c:if test="${busCustomField.updateTime > 0}">
                                <jsp:useBean id="updateTime" class="java.util.Date"/>
                                <jsp:setProperty name="updateTime" property="time" value="${busCustomField.updateTime}"/>
                                <fmt:formatDate value="${updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td style="text-align: left;">
                            <c:if test="${releaseStatus  == 0}">
                                <span><a id="a_${busCustomField.fieldName}" href="javascript:removeField('${busCustomField.tableName}','${busCustomField.fieldName}');" target="rightFrame"><img src="${baseUrl}/img/bianji.png">删除</a></span>
                            </c:if>
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
                        <li><a href="${baseUrl}/web/field/list.do?tableName=${tableName}&page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/field/list.do?tableName=${tableName}&page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>