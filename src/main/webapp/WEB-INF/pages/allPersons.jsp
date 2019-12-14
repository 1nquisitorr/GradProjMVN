<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %><%----%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="<c:url value="/res/css/style.css"/>" rel="stylesheet" type="text/css"/>
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
<body class="body">

<div class="infoWindow">

    <p>Logged as <c:out value="${pageContext.request.remoteUser}"/></p>
    <a href="/upload">upload</a>
    <a href="/admin">upload</a>
    <form action="/logout" method="post">
        <input type="submit" value="Log out"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <c:choose>
        <c:when test="${personAddMenu == true}">
            <c:url value="/add" var="addUrl"/>
            <c:url value="/edit" var="editUrl"/>
            <form action="${empty person.name ? addUrl : editUrl}?${_csrf.parameterName}=${_csrf.token}" method="POST"
                  enctype="multipart/form-data">
                <c:choose>
                    <c:when test="${!empty person.name}">
                        <p>Edit Person</p>
                        <input type="hidden" name="id" value="${person.id}">
                    </c:when>
                    <c:otherwise>
                        <p>Add new Person: </p>
                    </c:otherwise>
                </c:choose>

                <p><input type="text" name="name" placeholder="Name" value="${person.name}" maxlength="30" required>
                <p><input type="text" name="surname" placeholder="Last Name" value="${person.surname}" maxlength="30"
                          required>
                <p><input type="date" id="datepicker" name="date"></p>
                <p><select name="crime">
                    <c:forEach var="crime" items="${CrimeActionList}">
                        <option value=${crime.id}>${crime.description}</option>
                        <h3>${crime.id}</h3>>
                    </c:forEach>
                </select></p>
                <p>
                    <c:set value="add" var="add"/>
                    <c:set value="edit" var="edit"/>
                    <input type="file" name="file"/>

                    <input type="submit" value="${empty person.name ? add : edit}">
                </p>
                <p>${message}</p>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                <a style="color: yellow" href="/all"> Check Person</a>
            </c:if>
        </c:when>
        <c:otherwise>
            <form action="/checkPerson" name="person" method="POST">
                <h3>Check Person</h3>
                <p><input type="text" name="name" placeholder="Name" value="${Person.name}" maxlength="30">
                <p><input type="text" name="surname" placeholder="Last Name" value="${person.surname}" maxlength="30">
                <p><input type="date" id="datepicker2" name="birthDate"></p>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" value="Submit">
            </form>
            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                <a style="color: yellow" href="/add"> Add Person</a>
            </c:if>
        </c:otherwise>

    </c:choose>


</div>


<div class="tableDiv">
    <c:choose>
    <c:when test="${personMenuValid == true}">
    <img src="data:image/jpeg;base64, ${img}" height="300" width="250">

    <h1 style="color: yellow">${personMenu.name} ${personMenu.surname}</h1>
    <h3 style="color: yellow">${personMenu.birthDate}</h3>
    <h2 style="color: yellow">${crime.description}</h2>
    <h3 style="color: yellow">${crime.descriptionFull}</h3>
    <a style="color: yellow" href="/all">Back to List</a>

</div>

</c:when>
<c:otherwise>
    <table>
        <a href="/all" class="headLink">PERSONS</a>

        <c:if test="${personsCount > 0}">
            <tr class="headTable">
                <th>ID</th>
                <th>name</th>
                <th>Last Name</th>
                <th>Date of birth</th>
                <th>Some Info</th>
                <th>action</th>
            </tr>
            <c:forEach var="persons" items="${personsList}">
                <tr class="mainTable">
                    <td>${persons.id}</td>
                    <td>${persons.name}</td>
                    <td>${persons.surname}</td>
                    <td>${persons.birthDate}</td>
                    <td>no info</td>
                    <td>
                        <c:if test="${not empty pageContext.request.userPrincipal}">
                            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                                <a style="color: yellow" href="/edit/${persons.id}">edit</a>
                                <a style="color: yellow" href="/delete/${persons.id}">delete</a>
                            </c:if>
                        </c:if>
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


</body>
</html>


