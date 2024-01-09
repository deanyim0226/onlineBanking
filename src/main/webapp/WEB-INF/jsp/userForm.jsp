<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>User Form</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){

        })
    </script>
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
        <h1>User Form</h1>
        <f:form action="saveUser" method="POST" modelAttribute="user">

            <table>
                <c:if test="${hasError}">
                    <tr>
                        <td>Errors</td>
                        <td><f:errors path="*"></f:errors></td>
                    </tr>
                </c:if>

                <tr>
                    <td>ID</td>
                    <td><f:input path="userId"/></td>
                </tr>

                <tr>
                    <td>NAME</td>
                    <td><f:input path="username"/></td>
                </tr>
                <tr>
                    <td>PASSWORD</td>
                    <td><f:input path="password"/></td>
                </tr>
                <tr>
                    <td>EMAIL</td>
                    <td><f:input path="email"/></td>
                </tr>


                <tr>

                    <td>Roles</td>
                    <td>
                        <c:forEach items="${roles}" var="role">
                            <c:if test="${retrievedRole.contains(role)}">
                                <f:checkbox path="roles" label="${role.getName()}" value="${role.getRoleId()}" checked="ture"/>
                            </c:if>
                            <c:if test="${!retrievedRole.contains(role)}">
                                <f:checkbox path="roles" label="${role.getName()}" value="${role.getRoleId()}"/>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" align="center"><input class="btn btn-primary" type="submit" value="submit"/> </td>
                </tr>

            </table>

        </f:form>
    </div>

    <div class="container-sm" align="center">
        <h2>User Record</h2>
        <table class="table table-dark table-striped">
            <tr>

                <th>ID</th>

                <th>NAME</th>
                <th>PASSWORD</th>
                <th>EMAIL</th>
                <th>ROLES</th>
                <th colspan="2">Actions</th>
            </tr>

            <c:forEach items="${users}" var="user">
                <tr>

                    <td>${user.getUserId()}</td>
                    <td>${user.getUsername()}</td>
                    <td>${user.getPassword()}</td>
                    <td>${user.getEmail()}</td>

                    <td>
                        <c:forEach items="${user.getRoles()}" var="role">
                            ${role.getName()}
                        </c:forEach>
                    </td>
                    <td><a href="updateUser?userId=${user.getUserId()}">Update</a> </td>
                    <s:authorize access="hasAuthority('Admin')">
                    <td><a href="deleteUser?userId=${user.getUserId()}">Delete</a> </td>
                    </s:authorize>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>