<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据表</title>
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
                url: "${baseUrl}/web/entity/save.do",
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
                url: "${baseUrl}/web/entity/add.do",
                type: "POST",
                data: baseObj,
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#id").val(result.data.id);
                        $("#doc-ipt-tableCode-1").val(result.data.tableCode);
                        $("#doc-ipt-tabName-1").attr("readonly", "true");
                        $("#doc-ipt-createAid-1").val(result.data.createAid);
                        $("#doc-ipt-createTime-1").val(result.data.createTime);
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
            <a href="javascript:history.go(-1);">返回</a><span>></span><a href="javascript:void(0)">数据表</a>
        </div>
        <div class="second">
            <form id="form">
                <input type="hidden" id="id" name="id" value="${busCustomEntity.id}"/>

                <div>
                    <span class="title">表名</span>
                    <input id="doc-ipt-tableName-1" type="text" name="tableName" value="${busCustomEntity.tableName}" <c:if test="${busCustomEntity.id != null}">readonly</c:if>/>
                </div>

                <div>
                    <span class="title">中文名称</span>
                    <input id="doc-ipt-inChinese-1" type="text" name="inChinese" value="${busCustomEntity.inChinese}"/>
                </div>
                <div>
                    <span class="title">回调地址</span>
                    <input id="doc-ipt-callbackUrl-1" type="text" name="callbackUrl" value="${busCustomEntity.callbackUrl}"/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">描述</span>
                    <textarea id="doc-ipt-description-1" name="description">${busCustomEntity.description}</textarea>
                </div>

                <div>
                    <c:if test='${busCustomEntity.id  == null}'>
                        <span id="btn_add" class="save" onclick="add();">添加</span>
                    </c:if>
                    <c:if test='${busCustomEntity.id != null}'>
                        <span class="save" onclick="save();">保存</span>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>