<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>平台表权限</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/form.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/menu.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/jquery.extend.js"></script>

    <script type="text/javascript">

        function add() {
            var form = $('#form');
            var baseObj = form.serializeObject();
            $.ajax({
                url: "${baseUrl}/web/common/priAdd.do",
                type: "POST",
                data: baseObj,
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#btn_add").remove();
                        alert('添加成功');
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first">
            <a href="javascript:history.go(-1);">返回</a><span>> 对接系统 > </span><a href="javascript:void(0)">平台表权限</a>
        </div>
        <div class="second">
            <form id="form">
                <input type="hidden" id="id" name="id" value="${entityPrivilege.id}"/>

                <div>
                    <span class="title">平台编码</span>
                    <input id="doc-ipt-platformCode-1" type="text" name="platformCode" value="${entityPrivilege.platformCode}" readonly/>
                </div>

                <div>
                    <span class="title">平台名称</span>
                    <input id="doc-ipt-platformName-1" type="text" name="platformName" value="${entityPrivilege.platformName}" readonly/>
                </div>

                <div>
                    <span class="title">系统表</span>
                    <select id="doc-ipt-tableName-1" name="tableName">
                        <c:forEach items="${tableList}" var="table">
                            <option value="${table.TABLE_NAME}" <c:if test="${entityPrivilege.tableName == table.TABLE_NAME}">selected="selected"</c:if>>${table.IN_CHINESE}</option>
                        </c:forEach>
                    </select>
                </div>

                <input type="hidden" name="priType" value="${priType}"/>

                <div>
                    <span id="btn_add" class="save" onclick="add();">添加</span>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>