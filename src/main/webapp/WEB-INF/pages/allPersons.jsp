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
    <script>

        function readURL(input) {
            if (input.files && input.files[0]) {

                var reader = new FileReader();

                reader.onload = function (e) {
                    $('.image-upload-wrap').hide();

                    $('.file-upload-image').attr('src', e.target.result);
                    $('.file-upload-content').show();

                    $('.image-title').html(input.files[0].name);
                };

                reader.readAsDataURL(input.files[0]);

            } else {
                removeUpload();
            }
        }

        function removeUpload() {
            $('.file-upload-input').replaceWith($('.file-upload-input').clone());
            $('.file-upload-content').hide();
            $('.image-upload-wrap').show();
        }

        $('.image-upload-wrap').bind('dragover', function () {
            $('.image-upload-wrap').addClass('image-dropping');
        });
        $('.image-upload-wrap').bind('dragleave', function () {
            $('.image-upload-wrap').removeClass('image-dropping');
        });
    </script>
</head>

<body class="body">
<ul id="menu-bar">
    <li class="active"><a href="/all">Home</a></li>
    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
        <li><a href="/add">Add new Person</a></li>
    </c:if>
    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
        <li><a href="/admin">Log Page</a></li>
    </c:if>
    <p class="loggedAs">Logged as ${pageContext.request.remoteUser} <a href="/login?logout">logout</a></p>

</ul>

<div class="infoWindow">

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
                                        <option value="false">Suspect is in contact with: </option>
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
                        </p>

                        <div class="form-group">
                            <div class="col-md-offset-3 col-sm-offset-2 col-sm-3">

                                <div class="file-upload"
                                     style=" background: linear-gradient(to right,#1c1d1e 10%,#1c3f40); margin-top:30px;">
                                    <button class="file-upload-btn" type="button"
                                            onclick="$('.file-upload-input').trigger( 'click' )">Add Image
                                    </button>

                                    <div class="image-upload-wrap">
                                        <input class="file-upload-input" type="file" onchange="readURL(this);"
                                               accept="image/*"
                                               name="file"/>
                                        <div class="drag-text">
                                            <h3>Image preview</h3>
                                        </div>
                                    </div>
                                    <div class="file-upload-content">
                                        <img class="file-upload-image" src="#" alt="your image"/>
                                        <div class="image-title-wrap">
                                            <button type="button" onclick="removeUpload()" class="remove-image">Remove
                                                <span
                                                        class="image-title">Uploaded Image</span></button>
                                        </div>
                                    </div>
                                    <input type="submit" class="btn btn-default"
                                           style="margin-top: 50px; margin-left: 10%; width: 80%;"
                                           value="${empty person.name ? add : edit}">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        </c:when>
        <c:otherwise>
<%--            CheckForm--%>
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

            <%--            CheckForm--%>

        </c:otherwise>
        </c:choose>

    </div>
</div>


<%--PERSON CARD--%>

<div class="tableDiv">
    <c:choose>
    <c:when test="${personMenuValid == true}">

    <div class="cardMainInfo">
        <img src="data:image/jpeg;base64, ${img}" height="300" width="280"><br>
        <a href="/edit/${personMenu.id}" style="margin-top: 15px; color: #39b7af; text-decoration: none; font-size: 20px; margin-left: 45%">EDIT</a>
    </div>

    <div class="cardSuspectIn">

        <div class="nameAndDate">

            <p class="cardName">${personMenu.name} ${personMenu.surname}</p>
            <p class="cardBirthDate">Date of Birth: ${personMenu.birthDate}</p>

        </div>

        <p class="cardCrimeName">Suspects in: <br>${crime.description}</p>
        <p class="cardCrimeDescription">Crime description according to national Law: <br>${crime.descriptionFull}</p>

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


    </div>


</div>


    <%--/PERSON CARD--%>


</c:when>
<c:otherwise>
    <table>

        <c:if test="${personsCount > 0}">
            <tr class="headTable">
                <th>ID</th>
                <th>name</th>
                <th>Last Name</th>
                <th>Date of birth</th>
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
<div>


</div>
</body>
</html>


