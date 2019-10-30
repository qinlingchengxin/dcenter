<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>监控</title>
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
                url: "${baseUrl}/web/monitor/save.do",
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
                url: "${baseUrl}/web/monitor/add.do",
                type: "POST",
                data: baseObj,
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#id").val(result.data.id);
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
            <a href="javascript:history.go(-1);">返回</a><span>></span><a href="javascript:void(0)">监控</a>
        </div>
        <div class="second">
            <form id="form">
                <input type="hidden" id="id" name="id" value="${busMonitor.id}"/>

                <div>
                    <span class="title">平台名称</span>
                    <select id="doc-ipt-platformCode-1" name="platformCode">
                        <c:forEach items="${platforms}" var="platform">
                            <option value="${platform.CODE}" <c:if test="${busMonitor.platformCode == platform.CODE}">selected="selected"</c:if>>${platform.PLAT_NAME}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <span class="title">监控方式</span>
                    <select id="doc-ipt-type-1" name="type">
                        <option value="0" <c:if test="${busMonitor.type == 0}">selected="selected"</c:if>>ping</option>
                        <option value="1" <c:if test="${busMonitor.type == 1}">selected="selected"</c:if>>telnet</option>
                    </select>
                </div>

                <div>
                    <span class="title">IP</span>
                    <input id="doc-ipt-ip-1" type="text" name="ip" value="${busMonitor.ip}"/>
                </div>

                <div>
                    <span class="title">端口</span>
                    <input id="doc-ipt-port-1" type="text" name="port" value="${busMonitor.port}"/>
                </div>

                <div>
                    <span class="title">间隔天</span>
                    <input id="doc-ipt-intervalDay-1" type="text" name="intervalDay" value="${busMonitor.intervalDay}"/>
                </div>

                <div>
                    <span class="title">间隔小时</span>
                    <input id="doc-ipt-intervalHour-1" type="text" name="intervalHour" value="${busMonitor.intervalHour}"/>
                </div>

                <div>
                    <span class="title">间隔分钟</span>
                    <input id="doc-ipt-intervalMinute-1" type="text" name="intervalMinute" value="${busMonitor.intervalMinute}"/>
                </div>

                <div>
                    <span class="title">是否通知</span>
                    <select id="doc-ipt-notice-1" name="notice">
                        <option value="0" <c:if test="${busMonitor.notice == 0}">selected="selected"</c:if>>否</option>
                        <option value="1" <c:if test="${busMonitor.notice == 1}">selected="selected"</c:if>>是</option>
                    </select>
                </div>

                <div>
                    <c:if test='${busMonitor.id  == null}'>
                        <span id="btn_add" class="save" onclick="add();">添加</span>
                    </c:if>
                    <c:if test='${busMonitor.id != null}'>
                        <span class="save" onclick="save();">保存</span>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>