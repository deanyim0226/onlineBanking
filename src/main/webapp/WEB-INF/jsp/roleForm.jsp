<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>Role Form</title>

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
    </div>
    <div align="center">

        <s:authorize access="hasAuthority('Admin')">
        <h1>Role Form</h1>
        <f:form action="saveRole" method="POST" modelAttribute="role">
            <table >
                <tr>
                    <td>ID</td>
                    <td><f:input path="roleId" value="${role.getRoleId()}"/></td>
                </tr>
                <tr>
                    <td>NAME</td>
                    <td><f:input path="name" value="${role.getName()}"/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input class="btn btn-primary" type="submit" value="submit"/> </td>
                </tr>
            </table>
        </f:form>
        </s:authorize>
    </div>

    <div class="container-sm" align="center" >
        <h2>Role Record</h2>
        <table class="table table-dark table-striped">
            <tr>
                <s:authorize access="hasAuthority('Admin')">
                <th>ID</th>
                </s:authorize>
                <th>NAME</th>
                <s:authorize access="hasAuthority('Admin')">
                <th colspan="2">Action</th>
                </s:authorize>
            </tr>
            <c:forEach items="${roles}" var="role">
                <tr>
                    <s:authorize access="hasAuthority('Admin')">
                    <td>${role.getRoleId() }</td>
                    </s:authorize>

                    <td>${role.getName()}</td>
                    <s:authorize access="hasAuthority('Admin')">
                    <td><a href="updateRole?roleId=${role.getRoleId()}">Update</a> </td>
                    <td><a href="deleteRole?roleId=${role.getRoleId()}">Delete</a> </td>
                    </s:authorize>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>