<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>监控</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript">
        function removeMonitor(id) {
            $.ajax({
                url: "${baseUrl}/web/monitor/remove.do?id=" + id,
                type: "GET",
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#tr_" + id).remove();
                        alert('删除成功');
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
        <div class="first clearfloat">
            <div class="fl title">监控</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/monitor/list.do" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">刷新</a></span>
                <span><a href="${baseUrl}/web/monitor/edit.do" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="6%">序号</th>
                    <th width="15%" class="self_th_td">平台名称</th>
                    <th width="6%">监控方式</th>
                    <th width="6%">IP</th>
                    <th width="6%">端口</th>
                    <th width="6%">间隔天</th>
                    <th width="6%">间隔小时</th>
                    <th width="6%">间隔分</th>
                    <th width="6%">是否通知</th>
                    <th width="6%">监控状态</th>
                    <th width="6%">传输数量</th>
                    <%--<th width="10%">创建时间</th>--%>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${monitors}" var="monitor" varStatus="vs">
                    <tr id="tr_${monitor.id}">
                        <td>${vs.index + 1}</td>
                        <td class="self_th_td">${monitor.platName}</td>
                        <td>
                            <c:if test="${monitor.type == 0}">ping</c:if>
                            <c:if test="${monitor.type == 1}">telnet</c:if>
                        </td>
                        <td>${monitor.ip}</td>
                        <td>${monitor.port}</td>
                        <td>${monitor.intervalDay}</td>
                        <td>${monitor.intervalHour}</td>
                        <td>${monitor.intervalMinute}</td>

                        <td>
                            <c:if test="${monitor.notice == 0}">否</c:if>
                            <c:if test="${monitor.notice == 1}">是</c:if>
                        </td>

                        <td>
                            <c:if test="${monitor.status == 0}">待监控</c:if>
                            <c:if test="${monitor.status == 1}"><span style="border:0; color: green;">正常</span></c:if>
                            <c:if test="${monitor.status == 2}"><span style="border:0; color: red;">中断</span></c:if>
                        </td>
                        <td>${monitor.upAmount + monitor.downAmount}</td>
                            <%-- <td>
                                 <c:if test="${monitor.createTime > 0}">
                                     <jsp:useBean id="createTime" class="java.util.Date"/>
                                     <jsp:setProperty name="createTime" property="time" value="${monitor.createTime}"/>
                                     <fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm"/>
                                 </c:if>
                             </td>--%>
                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/web/monitor/edit.do?id=${monitor.id}" target="rightFrame"><img src="${baseUrl}/img/bianji.png">编辑</a></span>
                            <span><a href="${baseUrl}/web/monitor/contact/list.do?mId=${monitor.id}" target="rightFrame"><img src="${baseUrl}/img/bianji.png">联系人</a></span>
                            <span id="btn_${monitor.id}"><a href="javascript:removeMonitor('${monitor.id}');" target="rightFrame"><img src="${baseUrl}/img/chakan.png">删除</a></span>
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
                        <li><a href="${baseUrl}/web/monitor/list.do?page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/monitor/list.do?page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>