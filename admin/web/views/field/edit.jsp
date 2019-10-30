<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>平台实体字段</title>
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
                url: "${baseUrl}/web/field/save.do",
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
                url: "${baseUrl}/web/field/add.do",
                type: "POST",
                data: baseObj,
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#emptyData").click();
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
            <a href="javascript:history.go(-1);">返回</a><span>></span><a href="javascript:void(0)">平台实体字段</a>
        </div>
        <div class="second">
            <form id="form">
                <input type="hidden" id="id" name="id" value="${busCustomField.id}"/>

                <div>
                    <span class="title">表名</span>
                    <input id="doc-ipt-tableName-1" type="text" name="tableName" value="${busCustomField.tableName}" readonly/>
                </div>

                <div>
                    <span class="title">字段名称</span>
                    <input id="doc-ipt-fieldName-1" type="text" name="fieldName" value="${busCustomField.fieldName}" <c:if test='${busCustomField.id != null}'>readonly</c:if>/>
                </div>

                <div>
                    <span class="title">中文名称</span>
                    <input id="doc-ipt-inChinese-1" type="text" name="inChinese" value="${busCustomField.inChinese}"/>
                </div>

                <div>
                    <span class="title">字段类型</span>
                    <select id="doc-ipt-fieldType-1" name="fieldType">
                        <option value="0" <c:if test="${busCustomField.fieldType == 0}">selected="selected"</c:if>>整型</option>
                        <option value="1" <c:if test="${busCustomField.fieldType == 1}">selected="selected"</c:if>>字符串</option>
                        <option value="2" <c:if test="${busCustomField.fieldType == 2}">selected="selected"</c:if>>时间戳</option>
                        <option value="3" <c:if test="${busCustomField.fieldType == 3}">selected="selected"</c:if>>浮点型</option>
                    </select>

                    <span class="title">是否必填</span>
                    <select id="doc-ipt-required-1" name="required">
                        <option value="0" <c:if test="${busCustomField.required == 0}">selected="selected"</c:if>>否</option>
                        <option value="1" <c:if test="${busCustomField.required == 1}">selected="selected"</c:if>>是</option>
                    </select>

                    <c:if test="${releaseTime ==0 }">
                        <span class="title">是否去重</span>
                        <select id="doc-ipt-uniqueKey-1" name="uniqueKey">
                            <option value="0" <c:if test="${busCustomField.uniqueKey == 0}">selected="selected"</c:if>>否</option>
                            <option value="1" <c:if test="${busCustomField.uniqueKey == 1}">selected="selected"</c:if>>是</option>
                        </select>
                    </c:if>

                    <span class="title">是否暴露</span>
                    <select id="doc-ipt-exposed-1" name="exposed">
                        <option value="0" <c:if test="${busCustomField.exposed == 0}">selected="selected"</c:if>>否</option>
                        <option value="1" <c:if test="${busCustomField.exposed == 1}">selected="selected"</c:if>>是</option>
                    </select>
                </div>

                <div>
                    <span class="title">字段长度</span>
                    <input id="doc-ipt-fieldLength-1" type="text" name="fieldLength" value="${busCustomField.fieldLength}"/>
                </div>

                <div>
                    <span class="title">字段精度</span>
                    <input id="doc-ipt-precisions-1" type="text" name="precisions" value="${busCustomField.precisions}"/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">值域</span>
                    <textarea id="doc-ipt-valueField-1" name="valueField">${busCustomField.valueField}</textarea><span>注：@@隔开</span>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">备注</span>
                    <textarea id="doc-ipt-remarks-1" name="remarks">${busCustomField.remarks}</textarea>
                </div>

                <div>
                    <c:if test='${busCustomField.id  == null}'>
                        <span id="btn_add" class="save" onclick="add();">添加</span>
                    </c:if>
                    <c:if test='${busCustomField.id != null}'>
                        <span class="save" onclick="save();">保存</span>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>