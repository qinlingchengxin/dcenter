<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>平台信息</title>
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/basic.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/form.css">
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/topAndBottom.css">
    <script type="text/javascript" src="${baseUrl}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/menu.js"></script>
    <script type="text/javascript" src="${baseUrl}/js/jquery.extend.js"></script>
</head>
<body>
<div class="content clearfloat">
    <div class="content_right fr">
        <div class="first">
            <a href="javascript:void(0)">平台信息</a>
        </div>
        <div class="second">
            <form id="form">

                <div>
                    <span class="title" style="vertical-align: top;">平台编码</span>
                    <input id="doc-ipt-code-1" type="text" name="code" value="${busPlatform.code}" readonly/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">平台名称</span>
                    <input id="doc-ipt-platName-1" type="text" name="platName" value="${busPlatform.platName}" readonly/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">地区</span>
                    <input id="doc-ipt-area-1" type="text" name="area" value="${busPlatform.area}" readonly/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">联系人</span>
                    <input id="doc-ipt-contactUsername-1" type="text" name="contactUsername" value="${busPlatform.contactUsername}" readonly/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">联系电话</span>
                    <input id="doc-ipt-contactPhone-1" type="text" name="contactPhone" value="${busPlatform.contactPhone}" readonly/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">传输方式</span>
                    <input id="doc-ipt-transType-1" type="text" name="transType" value="<c:if test="${busPlatform.transType == 2}">接口</c:if><c:if test="${busPlatform.transType == 0}">共享库</c:if><c:if test="${busPlatform.transType == 1}">共享目录</c:if>" readonly/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">共享目录</span>
                    <input id="doc-ipt-sharePath-1" type="text" name="sharePath" value="${busPlatform.sharePath}" readonly/>
                </div>

                <div>
                    <span class="title" style="vertical-align: top;">私钥</span>
                    <textarea id="doc-ipt-privateKey-1" name="privateKey" readonly>${busPlatform.privateKey}</textarea>
                </div>
            </form>

            <div>
            </div>
        </div>
    </div>
</div>
</body>
</html>