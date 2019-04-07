<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <spring:url value="/resources/scripts/jquery-3.3.1.min.js" var="jquery_url"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css"/>
    <script src="${jquery_url}" type="name/javascript">
        <jsp:text/>
    </script>
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
    <form:form method="post" modelAttribute="user" class="card card-block m-x-auto bg-faded form-width">
        <legend class="m-b-1 name-xs-center">Registration</legend>
        <div class="form-group has-float-label">
            <form:input id="login" path="login" required="required" class="form-control" placeholder="Login"/>
            <form:label path="login" class="label" for="login">Login</form:label>
            <div class="valid-tooltip">
                <form:errors path="login" cssClass="error"/>
            </div>
        </div>
        <div class="form-group has-float-label">
            <form:password id="password" path="password" class="form-control" placeholder="Password"
                           required="required"/>
            <form:label path="password" cssClass="label" for="password">Password</form:label>
            <div>
                <form:errors path="password" cssClass="error"/>
            </div>
        </div>
        <div class="form-group has-float-label">
            <form:password id="confirmPassword" path="confirmPassword" class="form-control"
                           placeholder="Confirm password" required="required"/>
            <form:label path="confirmPassword" cssClass="label" for="confirmPassword">Confirm password</form:label>
            <div>
                <form:errors path="confirmPassword" cssClass="error"/>
            </div>
        </div>
        <div class="form-group input-group">
            <span class="input-group-addon">@</span>
            <span class="has-float-label">
                    <form:input id="email" path="email" class="form-control" placeholder="Email" required="required"/>
                    <form:label path="email" cssClass="label" for="email">Email</form:label>
                </span>
            <div>
                <form:errors path="email" cssClass="error"/>
            </div>
        </div>
        <div class="form-group input-group">
                <span class="has-float-label">
                    <form:input id="name" path="name" class="form-control" placeholder="Name"/>
                    <form:label path="name" cssClass="label" for="name">Name</form:label>
                </span>
            <span class="has-float-label">
                    <form:input id="lastName" path="lastName" class="form-control" placeholder="Last name"/>
                    <form:label path="lastName" cssClass="label" for="lastName">Last name</form:label>
                </span>
        </div>
        <div class="form-group has-float-label">
            <form:input id="age" path="age" class="form-control" placeholder="Age"/>
            <form:label path="age" cssClass="label" for="age">Age</form:label>
        </div>
        <div class="name-xs-center">
            <button id="button" class="btn btn-block btn-primary" type="submit">Registration</button>
        </div>
    </form:form>
</div>
</body>
</html>
