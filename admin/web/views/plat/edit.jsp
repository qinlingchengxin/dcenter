<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>系统对接</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/form.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/menu.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/jquery.extend.js"></script>

    <script type="text/javascript">

        function save() {
            var selectNode = $("#doc-ipt-areaId-1");
            var val = selectNode.val();
            var optionNode = selectNode.find("option[value='" + val + "']");
            $("#doc-ipt-area-1").val(optionNode.text());

            var form = $('#form');
            var baseObj = form.serializeObject();
            $.ajax({
                url: "${baseUrl}/web/plat/save.do",
                type: "POST",
                data: baseObj,
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#doc-ipt-updateAid-1").val(result.data.updateAid);
                        $("#doc-ipt-updateTime-1").val(result.data.updateTime);
                        alert('保存成功');
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }

        function add() {
            var selectNode = $("#doc-ipt-areaId-1");
            var val = selectNode.val();
            var optionNode = selectNode.find("option[value='" + val + "']");
            $("#doc-ipt-area-1").val(optionNode.text());

            var form = $('#form');
            var baseObj = form.serializeObject();
            $.ajax({
                url: "${baseUrl}/web/plat/add.do",
                type: "POST",
                data: baseObj,
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#id").val(result.data.id);
                        $("#doc-ipt-code-1").val(result.data.code);
                        $("#doc-ipt-createAid-1").val(result.data.createAid);
                        $("#doc-ipt-createTime-1").val(result.data.createTime);
                        $("#doc-ipt-updateAid-1").val(result.data.createAid);
                        $("#doc-ipt-updateTime-1").val(result.data.createTime);
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
            <a href="javascript:history.go(-1);">返回</a><span>></span><a href="javascript:void(0)">系统对接</a>
        </div>
        <div class="second">
            <form id="form">
                <input type="hidden" id="id" name="id" value="${busPlatform.id}"/>

                <div>
                    <span class="title">平台编码</span>
                    <input id="doc-ipt-code-1" type="text" name="code" value="${busPlatform.code}" readonly/>
                </div>

                <div>
                    <span class="title">平台名称</span>
                    <input id="doc-ipt-platName-1" type="text" name="platName" value="${busPlatform.platName}"/>
                </div>

                <div>
                    <span class="title">地区</span>
                    <input id="doc-ipt-area-1" type="hidden" name="area" value="${busPlatform.area}"/>

                    <select id="doc-ipt-areaId-1" name="areaId">
                        <c:forEach items="${areaList}" var="area">
                            <option value="${area.id}" <c:if test="${busPlatform.areaId == area.id}">selected="selected"</c:if>>${area.vs}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <span class="title">联系人</span>
                    <input id="doc-ipt-contactUsername-1" type="text" name="contactUsername" value="${busPlatform.contactUsername}"/>
                </div>

                <div>
                    <span class="title">联系电话</span>
                    <input id="doc-ipt-contactPhone-1" type="text" name="contactPhone" value="${busPlatform.contactPhone}"/>
                </div>

                <div>
                    <span class="title">传输方式</span>
                    <select id="doc-ipt-transType-1" name="transType">
                        <option value="2" <c:if test="${busPlatform.transType == 2}">selected="selected"</c:if>>接口</option>
                        <option value="0" <c:if test="${busPlatform.transType == 0}">selected="selected"</c:if>>共享库</option>
                        <option value="1" <c:if test="${busPlatform.transType == 1}">selected="selected"</c:if>>共享目录</option>
                    </select>
                </div>

                <div>
                    <span class="title">共享目录</span>
                    <input id="doc-ipt-sharePath-1" type="text" name="sharePath" value="${busPlatform.sharePath}"/>
                </div>

                <div>
                    <c:if test='${busPlatform.id  == null}'>
                        <span id="btn_add" class="save" onclick="add();">添加</span>
                    </c:if>
                    <c:if test='${busPlatform.id != null}'>
                        <span class="save" onclick="save();">保存</span>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>