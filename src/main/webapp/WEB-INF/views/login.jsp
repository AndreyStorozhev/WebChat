<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Логин</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css"/>
    <style>
        .form-width {
            max-width: 25rem;
        }

        .has-float-label {
            position: relative;
        }

        .has-float-label label {
            position: absolute;
            left: 0;
            top: 0;
            cursor: name;
            font-size: 75%;
            opacity: 1;
            -webkit-transition: all .2s;
            transition: all .2s;
            top: -.5em;
            left: 0.75rem;
            z-index: 3;
            line-height: 1;
            padding: 0 1px;
        }

        .has-float-label label::after {
            content: " ";
            display: block;
            position: absolute;
            background: white;
            height: 2px;
            top: 50%;
            left: -.2em;
            right: -.2em;
            z-index: -1;
        }

        .has-float-label .form-control::-webkit-input-placeholder {
            opacity: 1;
            -webkit-transition: all .2s;
            transition: all .2s;
        }

        .has-float-label .form-control:placeholder-shown:not(:focus)::-webkit-input-placeholder {
            opacity: 0;
        }

        .has-float-label .form-control:placeholder-shown:not(:focus) + label {
            font-size: 150%;
            opacity: .5;
            top: .3em;
        }

        .input-group .has-float-label {
            display: table-cell;
        }

        .input-group .has-float-label .form-control {
            border-radius: 0.25rem;
        }

        .input-group .has-float-label:not(:last-child) .form-control {
            border-bottom-right-radius: 0;
            border-top-right-radius: 0;
        }

        .input-group .has-float-label:not(:first-child) .form-control {
            border-bottom-left-radius: 0;
            border-top-left-radius: 0;
            margin-left: -1px;
        }
    </style>
</head>
<body>
<div class="p-x-1 p-y-3">
    <form method="post" action="<c:url value="/login"/>" class="card card-block m-x-auto bg-faded form-width">
        <legend class="m-b-1 name-xs-center">Login in</legend>
        <span>${message}</span>
        <div class="form-group has-float-label">
            <input id="login" name="login" type="text" placeholder="Login" class="form-control"/>
            <label for="login">Login</label>
        </div>
        <div class="form-group has-float-label">
            <input id="password" name="password" type="password" class="form-control" placeholder="Password"/>
            <label for="password">Password</label>
        </div>
        <span>${error}</span>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="name-xs-center">
            <button class="btn btn-block btn-primary" type="submit">Log In</button>
        </div>
        <h4 class="name-center"><a href="<c:url value="/registration"/>">Create new account</a></h4>
    </form>
</div>
</body>
</html>
