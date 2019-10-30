<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>日志</title>
</head>
<body>
<ul>
    <c:forEach items="${files}" var="file">
        <li>
            <a style="text-decoration: none; font-size: 25px;" target="_blank" href="content.do?path=${file.url}">${file.name}</a>
        </li>
    </c:forEach>
</ul>
</body>
</html>