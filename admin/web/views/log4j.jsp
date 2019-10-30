<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>日志</title>
    <style type="text/css">

        div {
            width: 98%;
            text-align: center;
        }

        table {
            width: 98%;
            border: 1px solid #F4F4F4;
            border-radius: 5px;
            border-collapse: collapse;
            margin: 0 auto;
        }

        tr, th, td {
            border: 1px solid #F4F4F4;
        }
    </style>
</head>
<body>
<table>
    <thead>
    <tr style="background-color: #2F75B5;color: white;">
        <th>序号</th>
        <th>日志文件</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${files}" var="file" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td style="text-align: left;">
                <a style="text-decoration: none; font-size: 25px;" target="_blank" href="content.do?path=${file.url}">${file.name}</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>