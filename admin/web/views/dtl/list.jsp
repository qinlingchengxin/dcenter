<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据传输日志</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/index.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/My97DatePicker/WdatePicker.js"></script>

    <script type="text/javascript">
        function searchData() {
            var tableName = document.getElementById("tableName").value;
            var platformCode = document.getElementById("platformCode").value;
            var status = document.getElementById("status").value;
            var startTime = document.getElementById("startTime").value;
            var endTime = document.getElementById("endTime").value;
            var content = encodeURI(document.getElementById("content").value);
            window.location = "${baseUrl}/web/log/dataTrans.do?tableName=" + tableName + "&platformCode=" + platformCode + "&status=" + status + "&type=${type}&content=" + content + "&startTime=" + startTime + "&endTime=" + endTime;
        }
    </script>
</head>
<body>

<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first clearfloat">
            <div class="fl title">数据传输日志</div>
            <div class="fr operation">
            </div>
        </div>

        <div class="second">
            <form>
                <span>数据表：</span>
                <select id="tableName">
                    <option value=""></option>
                    <c:forEach items="${entities}" var="entity">
                        <option value="${entity.TABLE_NAME}"
                                <c:if test="${entity.TABLE_NAME == tableName}">selected="selected" </c:if> >${entity.IN_CHINESE}</option>
                    </c:forEach>
                </select>
                <span>平台：</span>
                <select id="platformCode">
                    <option value=""></option>
                    <c:forEach items="${platforms}" var="platform">
                        <option value="${platform.CODE}"
                                <c:if test="${platform.CODE == platformCode}">selected="selected" </c:if> >${platform.PLAT_NAME}</option>
                    </c:forEach>
                </select>

                <span>传输状态：</span>
                <select id="status">
                    <option value="-1">全部</option>
                    <option value="0"
                            <c:if test="${status == 0}">selected="selected" </c:if> >失败
                    </option>
                    <option value="1"
                            <c:if test="${status == 1}">selected="selected" </c:if> >成功
                    </option>
                </select>

                <span> 起始时间：</span>
                <input style="width: 120px; height: 26px;background-color: #ffffff;color:#000;border: 1px solid #EAEBE8;cursor: auto;" type="text" id="startTime" value="${startTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'},false)" readonly/>

                <span> 结束时间：</span>
                <input style="width: 120px; height: 26px;background-color: #ffffff;color:#000;border: 1px solid #EAEBE8;cursor: auto;" type="text" id="endTime" value="${endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'},false)" readonly/>

                <span>传输内容：</span>
                <input style="width: 120px; height: 26px;background-color: #ffffff;color:#000;border: 1px solid #EAEBE8;cursor: auto;" type="text" id="content" value=""/>
                <input type="button" onclick="searchData();" value="搜索"/>
                <input type="reset" value="重置"/>
            </form>
        </div>

        <div class="third">
            <table>
                <tr>
                    <th width="5%">序号</th>
                    <th width="6%">平台编码</th>
                    <th width="10%">表名</th>
                    <th width="5%">传输状态</th>
                    <th width="20%">传输内容</th>
                    <c:if test="${type == 0}">
                        <th width="15%">无效数据</th>
                    </c:if>
                    <th width="10%">时间</th>
                    <th style="text-align: left;">操作</th>
                </tr>
                <c:forEach items="${dataTransLogs}" var="dataTransLog" varStatus="vs">
                    <tr>
                        <td>${vs.index + 1}</td>
                        <td>${dataTransLog.platformCode}</td>
                        <td>${dataTransLog.tableName}</td>
                        <td>
                            <c:if test="${dataTransLog.status == 0}">失败</c:if>
                            <c:if test="${dataTransLog.status == 1}">成功</c:if>
                        </td>
                        <td class="self_th_td"><c:out value="${dataTransLog.content}" escapeXml="true"/></td>
                        <c:if test="${dataTransLog.transType == 0}">
                            <td class="self_th_td"><c:out value="${dataTransLog.failedContent}" escapeXml="true"/></td>
                        </c:if>
                        <td>
                            <c:if test="${dataTransLog.createTime > 0}">
                                <jsp:useBean id="createTime" class="java.util.Date"/>
                                <jsp:setProperty name="createTime" property="time" value="${dataTransLog.createTime}"/>
                                <fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm"/>
                            </c:if>
                        </td>
                        <td style="text-align: left;">
                            <span><a href="${baseUrl}/web/log/dataDetail.do?logId=${dataTransLog.id}" target="rightFrame"><img src="${baseUrl}/img/chakan.png">数据详情</a></span>
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
                        <li><a href="${baseUrl}/web/log/dataTrans.do?page=${currPage - 1}&type=${type}&platformCode=${platformCode}&tableName=${tableName}">上一页</a></li>
                    </c:if>
                    <li id="currPage">${currPage}</li>
                    <c:if test="${currPage >= totalPage}">
                        <li><a href="#">下一页</a></li>
                    </c:if>
                    <c:if test="${currPage < totalPage}">
                        <li><a href="${baseUrl}/web/log/dataTrans.do?page=${currPage + 1}&type=${type}&platformCode=${platformCode}&tableName=${tableName}">下一页</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>