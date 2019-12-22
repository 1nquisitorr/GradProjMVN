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
<ul id="menu-bar">
    <li class="active"><a href="/all">Home</a></li>
    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
        <li><a href="/add">Add new Person</a></li>
    </c:if>
    <li><a href="#">All Photos</a></li>
    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
        <li><a href="/admin">Log Page</a></li>
    </c:if>
    <p class="loggedAs">Logged as ${pageContext.request.remoteUser} <a href="/login?logout">logout</a></p>

</ul>

<div class="infoWindow">


    <%--            <form action="${empty person.name ? addUrl : editUrl}?${_csrf.parameterName}=${_csrf.token}" method="POST"--%>
    <%--                  enctype="multipart/form-data">--%>
    <%--                <c:choose>--%>
    <%--                    <c:when test="${!empty person.name}">--%>
    <%--                        <p>Edit Person</p>--%>
    <%--                        <input type="hidden" name="id" value="${person.id}">--%>
    <%--                    </c:when>--%>
    <%--                    <c:otherwise>--%>
    <%--                        <p>Add new Person: </p>--%>
    <%--                    </c:otherwise>--%>
    <%--                </c:choose>--%>

    <%--                <p><input class="inputString" type="text" name="name" placeholder="Name" value="${person.name}"--%>
    <%--                          maxlength="30" required>--%>
    <%--                <p><input class="inputString" type="text" name="surname" placeholder="Last Name"--%>
    <%--                          value="${person.surname}" maxlength="30"--%>
    <%--                          required>--%>
    <%--                <p><input type="date" id="datepicker" name="date"></p>--%>
    <%--                <p><select name="crime">--%>
    <%--                    <c:forEach var="crime" items="${CrimeActionList}">--%>
    <%--                        <option value=${crime.id}>${crime.description}</option>--%>
    <%--                        &lt;%&ndash;                        <h3>${crime.id}</h3>>&ndash;%&gt;--%>
    <%--                    </c:forEach>--%>
    <%--                </select></p>--%>

    <%--                <p><select name="connections">--%>
    <%--                    <c:forEach var="connections" items="${personsList}">--%>
    <%--                        <option value=${connections.id}>${connections.surname}</option>--%>
    <%--                    </c:forEach>--%>
    <%--                </select></p>--%>
    <%--                <p>--%>
    <%--                    <c:set value="add" var="add"/>--%>
    <%--                    <c:set value="edit" var="edit"/>--%>
    <%--                    <input type="file" name="file"/>--%>

    <%--                    <input type="submit" value="${empty person.name ? add : edit}">--%>
    <%--                </p>--%>
    <%--                <p>${message}</p>--%>

    <%--                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
    <%--            </form>--%>

    <c:choose>
    <c:when test="${personAddMenu == true}">
    <c:url value="/add" var="addUrl"/>
    <c:url value="/edit" var="editUrl"/>
    <div class="form-bg">
        <div class="container">
            <div class="row">
                <div class="col-md-offset-3 col-md-6">
                    <form class="form-horizontal"
                          action="${empty person.name ? addUrl : editUrl}?${_csrf.parameterName}=${_csrf.token}"
                          method="POST" enctype="multipart/form-data">
                        <div class="form-group">


                            <c:choose>
                                <c:when test="${!empty person.name}">
                                    <p>Edit Person</p>
                                    <input type="hidden" name="id" value="${person.id}">
                                    <c:set var="date" value="${person.birthDate}"/>
                                </c:when>
                                <c:otherwise>
                                    <p>Add new Person: </p>
                                    <c:set var="date" value="01/01/1990"/>
                                </c:otherwise>
                            </c:choose>


                            <label class="col-md-3 col-sm-2 control-label" for="${person.name}">First
                                Name</label>
                            <div class="col-md-9 col-sm-10">
                                <input type="text" class="form-control" name="name" value="${person.name}"
                                       placeholder="Eg.Steve" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-2 control-label" for="${person.surname}">Last Name</label>
                            <div class="col-md-9 col-sm-10">
                                <input type="text" class="form-control" name="surname" value="${person.surname}"
                                       maxlength="30"
                                       placeholder="Eg.Smith" required>
                            </div>
                        </div>


                        <div class="form-group">
                            <p><input class="form-control" type="text" id="datepicker" name="date"
                                      placeholder=${date}></p>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 col-sm-2 control-label">Crimes</label>
                            <div class="col-md-9 col-sm-10">
                                <p><label>
                                    <select class="form-control" name="crime">
                                        <c:forEach var="crime" items="${CrimeActionList}">
                                            <option value=${crime.id}>${crime.description}</option>
                                        </c:forEach>
                                    </select>
                                </label></p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 col-sm-2 control-label">Connections</label>
                            <div class="col-md-9 col-sm-10">
                                <p><label>
                                    <select class="form-control" name="connections">
                                        <c:forEach var="connections" items="${personsList}">
                                            <option value=${connections.id}>${connections.surname}</option>
                                        </c:forEach>
                                    </select>
                                </label></p>
                            </div>
                        </div>

                        <p>
                            <c:set value="add" var="add"/>
                            <c:set value="edit" var="edit"/>
                            <input type="file" class="btn btn-default" name="file"/>
                        </p>

                        <div class="form-group">
                            <div class="col-md-offset-3 col-sm-offset-2 col-sm-3">
                                <input type="submit" class="btn btn-default" value="${empty person.name ? add : edit}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        </c:when>
        <c:otherwise>
            <%--            <form action="/checkPerson" name="person" method="POST">--%>

            <%--                <p><input class="inputString" type="text" name="name" placeholder="Name" value="${Person.name}"--%>
            <%--                          maxlength="30">--%>
            <%--                <p><input class="inputString" type="text" name="surname" placeholder="Last Name"--%>
            <%--                          value="${person.surname}" maxlength="30">--%>
            <%--                <p><input type="date" id="datepicker2" name="birthDate"></p>--%>
            <%--                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
            <%--                <input class="button" type="submit" value="Check Person">--%>
            <%--            </form>--%>


            <div class="form-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-md-offset-3 col-md-6">
                            <form class="form-horizontal"
                                  action="/checkPerson" name="person" method="POST">
                                <div class="form-group">
                                    <label class="col-md-3 col-sm-2 control-label" for="${person.name}">First
                                        Name</label>
                                    <div class="col-md-9 col-sm-10">
                                        <input type="text" class="form-control" name="name" value="${person.name}"
                                               placeholder="Eg.Steve">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 col-sm-2 control-label" for="${person.surname}">Last
                                        Name</label>
                                    <div class="col-md-9 col-sm-10">
                                        <input type="text" class="form-control" name="surname" value="${person.surname}"
                                               maxlength="30"
                                               placeholder="Eg.Smith">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <p><input class="form-control" type="text" id="datepicker2" name="birthDate"
                                              placeholder="01/01/2000"></p>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-offset-3 col-sm-offset-2 col-sm-3">
                                        <input type="submit" class="btn btn-default"
                                               value="Check Person">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>


        </c:otherwise>
        </c:choose>


<%--        &lt;%&ndash;WANTED CARD&ndash;%&gt;--%>
<%--        <c:choose>--%>
<%--            <c:when test="${personAddMenu !=true}">--%>
<%--                <div class="wantedClass">--%>
<%--                    <h1 style="color: yellow; text-align: center">Wanted</h1>--%>
<%--                    <img src="data:image/jpeg;base64, ${imgs}">--%>
<%--                    <h5>${wanted.name} ${wanted.surname}</h5>--%>
<%--                    <h5>Date of Birth: ${wanted.birthDate}</h5>--%>
<%--                </div>--%>
<%--            </c:when>--%>
<%--        </c:choose>--%>

        <%--WANTED CARD--%>


    </div>
</div>


<%--PERSON CARD--%>

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
    <%--/PERSON CARD--%>


</c:when>
<c:otherwise>
    <table>
            <%--        <a href="/all" class="headLink">PERSONS</a>--%>

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
                    <td style="text-align: center; color: yellow; width:5% ">${persons.id}</td>
                    <td>${persons.name}</td>
                    <td>${persons.surname}</td>
                    <td>${persons.birthDate}</td>
                    <td>no info</td>
                    <td>
                        <c:if test="${not empty pageContext.request.userPrincipal}">
                            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                                <a href="/edit/${persons.id}" class="editButton"></a>
                                <a href="/delete/${persons.id}" class="delete"></a>
                            </c:if>
                        </c:if>
                        <a class="show" href="/show/${persons.id}"></a>

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


