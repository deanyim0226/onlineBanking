<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>User Form</title>

</head>
<body>

<!--
    private Long userId;
    private String username;
    private String password;
    private String email;
    private List<Role> roles = new ArrayList<>();
-->
    <div align="center">
        <h1>User Form</h1>
        <f:form action="saveUser" method="POST" modelAttribute="user">

            <table>
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
                            <f:checkbox path="roles" label="${role.getName()}" value="${role.getRoleId()}"/>
                        </c:forEach>
                    </td>
                </tr>

            </table>

        </f:form>
    </div>

    <div align="center">
        <h2>User Record</h2>
        <table class="table table-dark table-striped">
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>PASSWORD</th>
                <th>EMAIL</th>
                <th>ROLES</th>
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
                    <td><a href="deleteUser?userId=${user.getUserId()}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>