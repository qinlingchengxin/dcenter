<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>对接系统</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title">对接系统</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/plat/list.do" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">刷新</a></span>
                <span><a href="${baseUrl}/web/plat/edit.do" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="10%">序号</th>
                    <th width="10%">平台编码</th>
                    <th width="10%">地区</th>
                    <th width="10%" class="self_th_td">平台名称</th>
                    <th width="10%">对接方式</th>
                    <th width="10%">修改时间</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${busPlatforms}" var="busPlatform" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>${busPlatform.code}</td>
                        <td>${busPlatform.area}</td>
                        <td class="self_th_td">${busPlatform.platName}</td>
                        <td>
                            <c:if test="${busPlatform.transType == 0}">共享库</c:if>
                            <c:if test="${busPlatform.transType == 1}">共享目录</c:if>
                            <c:if test="${busPlatform.transType == 2}">接口</c:if>
                        </td>
                        <td>
                            <c:if test="${busPlatform.updateTime > 0}">
                                <jsp:useBean id="updateTime" class="java.util.Date"/>
                                <jsp:setProperty name="updateTime" property="time" value="${busPlatform.updateTime}"/>
                                <fmt:formatDate value="${updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/web/plat/edit.do?code=${busPlatform.code}" target="rightFrame"><img src="${baseUrl}/img/bianji.png">编辑</a></span>
                            <span><a href="${baseUrl}/web/common/priList.do?priType=1&platformCode=${busPlatform.code}&platformName=${busPlatform.platName}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">上行表</a></span>
                            <span><a href="${baseUrl}/web/common/priList.do?priType=2&platformCode=${busPlatform.code}&platformName=${busPlatform.platName}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">下行表</a></span>
                            <span><a href="${baseUrl}/web/user/list.do?platformCode=${busPlatform.code}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">平台用户</a></span>
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
                        <li><a href="${baseUrl}/web/plat/list.do?page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/plat/list.do?page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>