<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>平台用户</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript">

        function resetPassword(id) {
            $.ajax({
                url: "${baseUrl}/web/user/resetPassword.do?id=" + id,
                type: "GET",
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        alert('修改成功');
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
            <div class="fl title"><a class="title" href="javascript:history.go(-1);">返回</a> > 对接系统 > 平台用户</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/user/list.do?platformCode=${platformCode}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">刷新</a></span>
                <span><a href="${baseUrl}/web/user/edit.do?platformCode=${platformCode}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="10%">序号</th>
                    <th width="10%">用户名</th>
                    <th width="10%">平台编码</th>
                    <th width="10%">平台名称</th>
                    <th width="10%">创建人</th>
                    <th width="10%">修改人</th>
                    <th width="10%">修改时间</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${busUsers}" var="busUser" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>${busUser.username}</td>
                        <td>${busUser.platformCode}</td>
                        <td>${busUser.platformName}</td>
                        <td>${busUser.createAid}</td>
                        <td>${busUser.updateAid}</td>
                        <td>
                            <c:if test="${busUser.updateTime > 0}">
                                <jsp:useBean id="updateTime" class="java.util.Date"/>
                                <jsp:setProperty name="updateTime" property="time" value="${busUser.updateTime}"/>
                                <fmt:formatDate value="${updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td style="text-align: left;">
                            <span><a href="javascript:resetPassword('${busUser.id}');" target="rightFrame"><img src="${baseUrl}/img/bianji.png">重置密码</a></span>
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
                        <li><a href="${baseUrl}/web/user/list.do?page=${currPage - 1}$platformCode=${platformCode}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/user/list.do?page=${currPage + 1}$platformCode=${platformCode}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>