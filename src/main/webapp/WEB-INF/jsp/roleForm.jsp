<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>Role Form</title>

</head>
<body>
    <div align="center">
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
    </div>

    <div class="container-sm" align="center" >
        <h2>Role Record</h2>
        <table class="table table-dark table-striped">
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th colspan="2">Action</th>
            </tr>
            <c:forEach items="${roles}" var="role">
                <tr>
                    <td>${role.getRoleId() }</td>
                    <td>${role.getName()}</td>
                    <td><a href="updateRole?roleId=${role.getRoleId()}">Update</a> </td>
                    <td><a href="deleteRole?roleId=${role.getRoleId()}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>