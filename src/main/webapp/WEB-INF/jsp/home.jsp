<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <title>Branch Form</title>
    <style>
        body{
            background-image: url("https://www.cpomagazine.com/wp-content/uploads/2023/02/what-u-s-companies-can-learn-from-the-european-payment-scene_1500.jpg");

        }

        h2, h3{
            color: honeydew;

        }
        a, p{
            color: honeydew;
            font-size: 1.3em;
        }
        div{
            margin-top: 10em;
        }

    </style>
</head>
<body>
<header>

    <nav class="navbar bg-dark border-bottom border-body" data-bs-theme="dark">

        <a class="btn btn-dark dropdown"   href="home"  >HOME</a>

        <ul class="nav justify-content-end">

            <li class = "nav-item"><a class="btn btn-dark dropdown"  href="userForm" >USER</a></li>
            <s:authorize access="hasAuthority('Admin')">
                <li class = "nav-item"><a class="btn btn-dark dropdown"  href="roleForm" >ROLE</a></li>
                <li class = "nav-item"><a class="btn btn-dark dropdown"  href="branchForm">BRANCH</a></li>
            </s:authorize>
            <li class = "nav-item"><a class="btn btn-dark dropdown"  href="customerForm">CUSTOMER</a></li>
            <li class = "nav-item"><a class="btn btn-dark dropdown"  href="accountForm">ACCOUNT</a></li>
            <li class = "nav-item"><a class="btn btn-dark dropdown"  href="atm">TRANSACTION</a></li>
            <li class = "nav-item"><a class="btn btn-dark dropdown"   href="searchForm">SEARCH </a></li>
            <s:authorize access="isAuthenticated()">
                <li class = "nav-item"><a class="btn btn-dark dropdown"  href="/logout">LOGOUT</a></li>
            </s:authorize>
        </ul>

    </nav>

</header>
<div align="center">

    <h2>Hello, ${user} </h2>
    <h3>WELCOME BACK TO OUR BANKING APPLICATION</h3>

    <div>
        <p>Learn about the <b><a href="roleForm" >roles</a> </b>that we provide </p>
        <br>
        <p>Look into our available  <b><a href="branchForm">branches</a></b></p>
    </div>

</div>
</body>
</html>