<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>监控联系人</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>

    <script type="text/javascript">
        function removeContact(id) {
            $.ajax({
                url: "${baseUrl}/web/monitor/contact/remove.do?id=" + id,
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
            <div class="fl title">监控联系人</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/monitor/contact/list.do?mId=${mId}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">刷新</a></span>
                <span><a href="${baseUrl}/web/monitor/contact/edit.do?mId=${mId}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="6%">序号</th>
                    <th width="10%">名称</th>
                    <th width="10%">手机号</th>
                    <th width="10%">邮箱</th>
                    <th width="10%">创建时间</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${contacts}" var="contact" varStatus="vs">
                    <tr id="tr_${contact.id}">
                        <td>${vs.index + 1}</td>
                        <td>${contact.name}</td>
                        <td>${contact.telNo}</td>
                        <td>${contact.email}</td>
                        <td>
                            <c:if test="${contact.createTime > 0}">
                                <jsp:useBean id="createTime" class="java.util.Date"/>
                                <jsp:setProperty name="createTime" property="time" value="${contact.createTime}"/>
                                <fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/web/monitor/contact/edit.do?id=${contact.id}" target="rightFrame"><img src="${baseUrl}/img/bianji.png">编辑</a></span>
                            <span id="btn_${contact.id}"><a href="javascript:removeContact('${contact.id}');" target="rightFrame"><img src="${baseUrl}/img/chakan.png">删除</a></span>
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
                        <li><a href="${baseUrl}/web/monitor/contact/list.do?mId=${mId}$page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/monitor/contact/list.do?mId=${mId}$page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>