<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>平台表权限</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript">

        function remove(id, index) {
            $.ajax({
                url: "${baseUrl}/web/common/removePri.do?id=" + id,
                type: "POST",
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#tr_" + index).remove();
                        alert('移除成功');
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
            <div class="fl title"><a class="title" href="javascript:history.go(-1);">返回</a> > 对接系统 > 平台表权限</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/common/priList.do?priType=${priType}&platformCode=${platformCode}&platformName=${platformName}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">刷新</a></span>
                <span><a href="${baseUrl}/web/common/priEdit.do?priType=${priType}&platformCode=${platformCode}&platformName=${platformName}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="10%">序号</th>
                    <th width="10%">平台编码</th>
                    <th width="10%">平台名称</th>
                    <th width="10%">表名称</th>
                    <th width="10%">权限类型</th>
                    <th width="10%">授权人</th>
                    <th width="10%">授权时间</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${entityPrivileges}" var="entityPrivilege" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>${entityPrivilege.platformCode}</td>
                        <td>${entityPrivilege.platformName}</td>
                        <td>${entityPrivilege.tableName}</td>

                        <td>
                            <c:if test="${entityPrivilege.priType == 0}">无权</c:if>
                            <c:if test="${entityPrivilege.priType == 1}">上行</c:if>
                            <c:if test="${entityPrivilege.priType == 2}">下行</c:if>
                        </td>
                        <td>${entityPrivilege.assignerAid}</td>
                        <td>
                            <c:if test="${entityPrivilege.assignerTime > 0}">
                                <jsp:useBean id="assignerTime" class="java.util.Date"/>
                                <jsp:setProperty name="assignerTime" property="time" value="${entityPrivilege.assignerTime}"/>
                                <fmt:formatDate value="${assignerTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td style="text-align: left;">
                            <span><a href="javascript:remove('${entityPrivilege.id}','${vs.index + 1}');" target="rightFrame"><img src="${baseUrl}/img/bianji.png">移除</a></span>
                            <c:if test="${entityPrivilege.priType == 2}">
                                <span><a href="${baseUrl}/web/entity/dataFilterList.do?epId=${entityPrivilege.id}" target="rightFrame"><img src="${baseUrl}/img/bianji.png">数据过滤</a></span>
                            </c:if>
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
                        <li><a href="${baseUrl}/web/common/priList.do?priType=${priType}&platformCode=${platformCode}&page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/common/priList.do?priType=${priType}&platformCode=${platformCode}&page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>