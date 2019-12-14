<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="<c:url value="/res/css/style.css"/>" rel="stylesheet" type="text/css"/>
</head>

<body>
<div class="tableDiv" style="width: 100%">
    <table>
        <a href="/all" style="color: yellow; font-size: 35px; margin-left: 50%;">Persons </a>
        <p style="font-size: 20px; color: yellow; margin-left: 53%">V. 0.1</p>
            <tr style="border: 3px green">
                <th>User Name</th>
                <th>Visited Page</th>
                <th>Action</th>
                <th>Daate of Action</th>
            </tr>
            <c:forEach var="list" items="${LogList}">
                <tr>
                    <td>${list.userName}</td>
                    <td>${list.visitedPage}</td>
                    <td>${list.action}</td>
                    <td>${list.date}</td>
                </tr>
            </c:forEach>

    </table>
</div>

</body>
</html>


