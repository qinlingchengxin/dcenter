<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>系统枚举值</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/form.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/menu.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/jquery.extend.js"></script>

    <script type="text/javascript">

        function save() {
            var form = $('#form');
            var baseObj = form.serializeObject();
            $.ajax({
                url: "${baseUrl}/web/sys/evSave.do",
                type: "POST",
                data: baseObj,
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        alert('保存成功');
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }

        function add() {
            var form = $('#form');
            var baseObj = form.serializeObject();
            $.ajax({
                url: "${baseUrl}/web/sys/evAdd.do",
                type: "POST",
                data: baseObj,
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#id").val(result.data.id);
                        $("#btn_add").attr("onclick", "save();").text("保存");
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
            <a href="javascript:history.go(-1);">返回</a><span>></span><a href="javascript:void(0)">系统枚举值</a>
        </div>
        <div class="second">
            <form id="form">
                <input type="hidden" id="id" name="id" value="${busEnumValue.id}"/>

                <div>
                    <span class="title">枚举代码</span>
                    <input id="doc-ipt-enumCode-1" type="text" name="enumCode" value="${busEnumValue.enumCode}" readonly/>
                </div>

                <div>
                    <span class="title">整形值</span>
                    <input id="doc-ipt-vi-1" type="text" name="vi" value="${busEnumValue.vi}"/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">字符串值</span>
                    <textarea id="doc-ipt-vs-1" name="vs">${busEnumValue.vs}</textarea>
                </div>

                <div>
                    <c:if test='${busEnumValue.id  == null}'>
                        <span id="btn_add" class="save" onclick="add();">添加</span>
                    </c:if>
                    <c:if test='${busEnumValue.id != null}'>
                        <span class="save" onclick="save();">保存</span>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>