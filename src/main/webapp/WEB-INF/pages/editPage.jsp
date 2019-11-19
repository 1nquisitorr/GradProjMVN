<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $(function () {
            $("#datepicker").datepicker();
        });

    </script>
</head>
<body>
<c:url value="/add" var="addUrl"/>
<c:url value="/edit" var="editUrl"/>
<form action="${empty person.name ? addUrl : editUrl}" name="person" method="POST">
    <c:choose>
        <c:when test="${!empty person.name}">
            <p>Edit Person</p>
            <input type="hidden" name="id" value="${person.id}">
        </c:when>
        <c:otherwise>
            <p>Add new Person</p>
        </c:otherwise>
    </c:choose>

    <p><input type="text" name="name" placeholder="Name" value="${person.name}" maxlength="30" required>
    <p><input type="text" name="surname" placeholder="Last Name" value="${person.surname}" maxlength="30" required>
    <p><input type="date" id="datepicker" name="birthDate"></p>
    <p><input type="text" name="Crimes" placeholder="Crimes" value="${person.crimes}" maxlength="10" required>
    <p>
        <c:set value="add" var="add"/>
        <c:set value="edit" var="edit"/>
        <input type="submit" value="${empty person.name ? add : edit}">
    </p>
    <p>${message}</p>
</form>
</body>
</html>
