<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>Branch Form</title>

</head>
<body>
<div align="center">
    <table>
        <tr>
            <td><a href="home">Home</a></td> <td> | </td>
            <td><a href="userForm">UserForm</a></td> <td> | </td>
            <td><a href="roleForm">RoleForm</a></td> <td> | </td>
            <td><a href="branchForm">BranchForm</a></td> <td> | </td>
            <td><a href="customerForm">CustomerForm</a></td> <td> | </td>
            <td><a href="accountForm">AccountForm</a></td> <td> | </td>
            <td><a href="atm">ATM</a></td><td> | </td>
            <td><a href="searchForm">Search</a></td>
            <s:authorize access="isAuthenticated()">
                <td> | </td>
                <td><a href="/logout">Logout</a></td>
            </s:authorize>
        </tr>
    </table>
    <h2>Hello, ${user} </h2>
    <h3>WELCOME BACK TO OUR BANKING APPLICATION</h3>
</div>
</body>
</html>