<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="shortcut icon" href="https://www.rudebox.org.ua/favicon.ico"/>
    <link rel='stylesheet prefetch' href='https://www.rudebox.org.ua/demo/lessons/styles/style.css'>
    <link rel="stylesheet" href="../res/css/login.css">
</head>

<body class="login-page">
<main>
    <div class="login-block">
        <img src="../res/img/1.png" alt="Scanfcode">
        <h1>Enter your data</h1>
        <p>admin login: admin; admin password: admin</p>
        <p>user login: user; user password: user</p>
        <form action="/login" method="post">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-user ti-user"></i></span>

                    <input type="text" class="form-control" name="username" placeholder="Login">

                </div>
            </div>
            <hr class="hr-xs">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-lock ti-unlock"></i></span>
                    <input type="password" class="form-control" name="password" placeholder="Password">
                </div>
            </div>

            <button class="btn btn-primary btn-block" type="submit">Enter</button>
            <c:if test="${param.error ne null}">
            <div class="alert-danger">Invalid username and password.</div>
            </c:if>
            <c:if test="${param.logout ne null}">
            <div class="alert-normal">You have been logged out.</div>
            </c:if>
    </div>
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    </form>
    </div>


</main>

</body>

</html>
