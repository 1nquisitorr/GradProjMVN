<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="<c:url value="/res/style.css"/>" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $(function () {
            $("#datepicker").datepicker();
        });
        $(function () {
            $("#datepicker2").datepicker();
        });
    </script>
</head>
<body>
<div class="tableDiv">
    <c:choose>
        <c:when test="${personMenuValid == true}">
            <h1 style="color: yellow">${personMenu.name} ${personMenu.surname}</h1>
            <%--            <h1 style="color: yellow">${personMenu.surname}</h1>--%>
            <h3 style="color: yellow">${personMenu.birthDate}</h3>
            <h2 style="color: yellow">${crime.description}</h2>
            <h3 style="color: yellow">${crime.descriptionFull}</h3>
            <a style="color: yellow" href="/">Back to List</a>
        </c:when>
        <c:otherwise>
            <table>
                <a href="/" style="color: yellow; font-size: 35px; margin-left: 50%;">Persons </a>
                <p style="font-size: 20px; color: yellow; margin-left: 53%">V. 0.1</p>
                <c:if test="${personsCount > 0}">
                    <tr style="border: 3px green">
                        <th>№</th>
                        <th>name</th>
                        <th>Last Name</th>
                        <th>Date of birth</th>
                        <th>Some Info</th>
                        <th>action</th>
                    </tr>
                    <c:forEach var="persons" items="${personsList}">
                        <tr>
                            <td>${persons.id}</td>
                            <td>${persons.name}</td>
                            <td>${persons.surname}</td>
                            <td>${persons.birthDate}</td>
                            <td>no info</td>
                            <td>
                                <a style="color: yellow" href="/edit/${persons.id}">edit</a>
                                <a style="color: yellow" href="/delete/${persons.id}">delete</a>
                                <a style="color: yellow" href="/show/${persons.id}">show</a>
                            </td>
                        </tr>

                    </c:forEach>
                </c:if>
                <c:if test="${personsCount == 0}">
                    <tr>
                        <td colspan="7" style="font-size: 150%; align: center">
                            Person not found
                        </td>
                    </tr>
                </c:if>

            </table>
        </c:otherwise>
    </c:choose>

</div>
<div class="infoWindow">

    <c:choose>
        <c:when test="${personAddMenu == true}">
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
                <p><input type="text" name="surname" placeholder="Last Name" value="${person.surname}" maxlength="30"
                          required>
                <p><input type="date" id="datepicker" name="birthDate"></p>


                <p><select name="Crimes">
                    <c:forEach var="crimes" items="${CrimeActionList}">
                        <option value=${crimes.id}>${crimes.description}</option>
                        <h3>${crimes.id}</h3>>
                    </c:forEach>
                </select></p>


                <p>
                    <c:set value="add" var="add"/>
                    <c:set value="edit" var="edit"/>
                    <input type="submit" value="${empty person.name ? add : edit}">
                </p>
                <p>${message}</p>

            </form>
            <a style="color: yellow" href="/"> Check Person</a>

        </c:when>
        <c:otherwise>
            <form action="/checkPerson" name="person" method="POST">
                <h3>Check Person</h3>
                <p><input type="text" name="name" placeholder="Name" value="${Person.name}" maxlength="30">
                <p><input type="text" name="surname" placeholder="Last Name" value="${person.surname}" maxlength="30">
                <p><input type="date" id="datepicker2" name="birthDate"></p>
                <input type="submit" value="Submit">
            </form>
            <a style="color: yellow" href="/add"> Add Person</a>

        </c:otherwise>

    </c:choose>

</div>
<div class="border">


</div>

</body>
</html>


