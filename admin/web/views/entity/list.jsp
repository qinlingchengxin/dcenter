<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据表</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript">
        function searchData() {
            var tableName = document.getElementById("s_table_name").value;
            window.location = "${baseUrl}/web/entity/list.do?tableName=" + tableName;
        }

        function removeTab(tableName) {
            $.ajax({
                url: "${baseUrl}/web/entity/remove.do?tableName=" + tableName,
                type: "GET",
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#tr_" + tableName).remove();
                        alert('删除成功');
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }

        function release(tableName) {
            $.ajax({
                url: "${baseUrl}/web/entity/release.do?tableName=" + tableName,
                type: "GET",
                dataType: "json",
                success: function (result) {
                    if (result.code == 1000) {
                        $("#re_" + tableName).text("已发布");
                        $("#a_" + tableName).remove();
                        $("#btn_" + tableName).remove();
                        alert('发布成功');
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
            <div class="fl title">数据表</div>
            <div class="fr operation">
                <span><a href="${baseUrl}/web/entity/list.do?tableName=${tableName}" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">刷新</a></span>
                <span><a href="${baseUrl}/web/entity/edit.do" target="rightFrame"> <img src="${baseUrl}/img/zengjia.png">新增</a></span>
            </div>
        </div>

        <div class="second">
            <form>
                <span>表名：</span>
                <input style="width: 120px; height: 26px;background-color: #ffffff;color:#000;border: 1px solid #EAEBE8;cursor: auto;" type="text" id="s_table_name" value="${tableName}"/>
                <input type="button" onclick="searchData();" value="搜索"/>
            </form>
        </div>

        <div class="third">
            <table>
                <tr>
                    <th width="4%">序号</th>
                    <th width="15%">表名</th>
                    <th width="15%" class="self_th_td">中文名称</th>
                    <%--<th width="8%">描述</th>--%>
                    <%--<th width="10%">修改时间</th>--%>
                    <th width="8%">发布人</th>
                    <th width="10%">发布时间</th>
                    <th width="8%">发布状态</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${busCustomEntities}" var="busCustomEntity" varStatus="vs">
                    <tr id="tr_${busCustomEntity.tableName}">
                        <td>${vs.index + 1}</td>
                        <td class="self_th_td" style="max-width: 150px;">${busCustomEntity.tableName}</td>
                        <td class="self_th_td" style="max-width: 150px;">${busCustomEntity.inChinese}</td>
                            <%--<td class="self_th_td">${busCustomEntity.description}</td>--%>
                            <%-- <td>
                                 <jsp:useBean id="updateTime" class="java.util.Date"/>
                                 <jsp:setProperty name="updateTime" property="time" value="${busCustomEntity.updateTime}"/>
                                 <fmt:formatDate value="${updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                             </td>--%>
                        <td>${busCustomEntity.releaseAid}</td>
                        <td>
                            <c:if test="${busCustomEntity.releaseTime > 0}">
                                <jsp:useBean id="releaseTime" class="java.util.Date"/>
                                <jsp:setProperty name="releaseTime" property="time" value="${busCustomEntity.releaseTime}"/>
                                <fmt:formatDate value="${releaseTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td id="re_${busCustomEntity.tableName}">
                            <c:if test="${busCustomEntity.releaseTime < busCustomEntity.updateTime}">未发布</c:if>
                            <c:if test="${busCustomEntity.updateTime <= busCustomEntity.releaseTime}">已发布</c:if>
                        </td>
                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/web/entity/edit.do?tableName=${busCustomEntity.tableName}" target="rightFrame"><img src="${baseUrl}/img/bianji.png">编辑</a></span>
                            <span><a href="${baseUrl}/web/field/list.do?tableName=${busCustomEntity.tableName}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">查看字段</a></span>
                            <span><a href="${baseUrl}/web/entity/dataTemplate.do?tableName=${busCustomEntity.tableName}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">数据模板</a></span>
                            <c:if test="${busCustomEntity.releaseTime < busCustomEntity.updateTime}">
                                <span id="a_${busCustomEntity.tableName}"><a href="javascript:release('${busCustomEntity.tableName}');" target="rightFrame"><img src="${baseUrl}/img/chakan.png">发布</a></span>
                            </c:if>

                            <c:if test="${busCustomEntity.releaseTime == 0}">
                                <span id="btn_${busCustomEntity.tableName}"><a href="javascript:removeTab('${busCustomEntity.tableName}');" target="rightFrame"><img src="${baseUrl}/img/chakan.png">删除</a></span>
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
                        <li><a href="${baseUrl}/web/entity/list.do?page=${currPage - 1}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/entity/list.do?page=${currPage + 1}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>