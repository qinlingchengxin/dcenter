<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据下载过滤</title>
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
                url: "${baseUrl}/web/entity/dataFilterSave.do",
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
                url: "${baseUrl}/web/entity/dataFilterAdd.do",
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
            <a href="javascript:history.go(-1);">返回</a><span>></span><a href="javascript:void(0)">数据下载过滤</a>
        </div>
        <div class="second">
            <form id="form">
                <input type="hidden" id="id" name="id" value="${platEntFilter.id}"/>
                <input type="hidden" id="epId" name="epId" value="${platEntFilter.epId}"/>

                <div>
                    <span class="title">组合</span>
                    <select id="doc-ipt-an-1" name="an">
                        <option value="AND" <c:if test="${platEntFilter.an == 'AND'}">selected="selected"</c:if>>AND</option>
                        <option value="OR" <c:if test="${platEntFilter.an == 'OR'}">selected="selected"</c:if>>OR</option>
                    </select>
                </div>
                <div>
                    <span class="title">字段</span>
                    <select id="doc-ipt-field-1" name="field">
                        <c:forEach items="${fields}" var="field">
                            <option value="${field.REAL_FIELD_NAME}" <c:if test="${field.REAL_FIELD_NAME == platEntFilter.field}">selected="selected"</c:if>>${field.FIELD_NAME}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <span class="title">条件</span>
                    <select id="doc-ipt-condition-1" name="condition">
                        <option value="=" <c:if test="${platEntFilter.condition == '='}">selected="selected"</c:if>>=</option>
                        <option value=">=" <c:if test="${platEntFilter.condition == '>='}">selected="selected"</c:if>>>=</option>
                        <option value="<=" <c:if test="${platEntFilter.condition == '<='}">selected="selected"</c:if>><=</option>
                        <option value="!=" <c:if test="${platEntFilter.condition == '!='}">selected="selected"</c:if>>!=</option>
                        <option value="like" <c:if test="${platEntFilter.condition == 'like'}">selected="selected"</c:if>>like</option>
                    </select>
                </div>
                <div>
                    <span class="title" style="vertical-align: top;">取值</span>
                    <input id="doc-ipt-conValue-1" type="text" name="conValue" value="${platEntFilter.conValue}"/>
                </div>

                <div>
                    <c:if test='${platEntFilter.id  == null}'>
                        <span id="btn_add" class="save" onclick="add();">添加</span>
                    </c:if>
                    <c:if test='${platEntFilter.id != null}'>
                        <span class="save" onclick="save();">保存</span>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>