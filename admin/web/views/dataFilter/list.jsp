<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据下载过滤</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript">

        function removeRecord(id) {
            $.ajax({
                url: "${baseUrl}/web/entity/dataFilterRemove.do?id=" + id,
                type: "GET",
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#tr_" + id).remove();
                        alert('移除成功!');
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
            <div class="fl title"><a class="title" href="javascript:history.go(-1);">返回</a> > 业务系统 > 数据下载过滤</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/entity/dataFilterEdit.do?epId=${epId}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>
        <div class="third">
            <table>
                <tr>
                    <th width="10%">序号</th>
                    <th width="10%">组合</th>
                    <th width="10%">字段</th>
                    <th width="10%">条件</th>
                    <th width="10%">取值</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${platEntFilters}" var="platEntFilter" varStatus="vs">
                    <tr id="tr_${platEntFilter.id}">
                        <td>${vs.index + 1}</td>
                        <td>${platEntFilter.an}</td>
                        <td>${platEntFilter.field}</td>
                        <td>${platEntFilter.condition}</td>
                        <td>${platEntFilter.conValue}</td>
                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/web/entity/dataFilterEdit.do?pnId=${platEntFilter.id}&epId=${platEntFilter.epId}" target="rightFrame"><img src="${baseUrl}/img/bianji.png">编辑</a></span>
                            <span><a href="javascript:removeRecord('${platEntFilter.id}');" target="rightFrame"><img src="${baseUrl}/img/chakan.png">移除</a></span>
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
                        <li><a href="${baseUrl}/web/entity/dataFilterList.do?epId=${epId}&page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/entity/dataFilterList.do?epId=${epId}&page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>