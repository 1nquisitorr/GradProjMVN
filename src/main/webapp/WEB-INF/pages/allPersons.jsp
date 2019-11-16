<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <name>CrimeSearch</name>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="table">
<table class="style">
    <caption>Persons</caption>
    <c:if test="${personsCount > 0}">
        <tr style="border: 3px green">
            <th >№</th>
            <th >name</th>
            <th>Last Name</th>
            <th>Date of birth</th>
            <th>Some Info</th>
            <th >action</th>
        </tr>
        <c:forEach var="persons" items="${personsList}" varStatus="i">
            <tr>
                <td class="left-side">${i.index + 1 + (page - 1) * 10}</td>
                <td class="name">${persons.name}</td>
                <td>${persons.lastName}</td>

                <td>${persons.birthDate}</td>
                <td>Danger</td>
                <td>
                    <a href="/edit/${persons.id}">
                        <span class="icon icon-edit"></span>
                        <a href="/delete/${persons.id}">
                        <span class="icon icon-delete"></span>
                        </a>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${personsCount == 0}">
        <tr>
            <td colspan="7" style="font-size: 150%" class="left-side right-side">
                the list is empty but you can add a new person
            </td>
        </tr>
    </c:if>
    <a style="color: white" href="/add">Add new Person</a>
</table>
</div>
</body>
</html>


