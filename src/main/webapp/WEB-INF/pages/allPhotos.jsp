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

<div class="connectedPersons">
    <p>Connected Persons: </p><br>
    <div class="connectedPersonPhotos">
        <c:forEach var="connectedPersonPhoto" items="${connectedPersonPhoto}">
            <img src="data:image/jpeg;base64, ${connectedPersonPhoto}" height="100" width="80">
        </c:forEach>
    </div>

    <div class="connectedPersonNamesDiv">
        <c:forEach var="connectedPerson" items="${connectedPerson}">
            <div class="connectedPersonNames">
                <a style="text-decoration: none; color:#39b7af;"
                   href="/show/${connectedPerson.id}">${connectedPerson.name} ${connectedPerson.surname}</a>
            </div>
        </c:forEach>
    </div>

</div>

</body>
</html>


