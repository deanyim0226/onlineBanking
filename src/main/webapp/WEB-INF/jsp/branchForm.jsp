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
                <td><a href="atm">ATM</a></td>
                <s:authorize access="isAuthenticated()">
                    <td> | </td>
                    <td><a href="/logout">Logout</a></td>
                </s:authorize>
            </tr>
        </table>
    </div>
    <s:authorize access="hasAuthority('Admin')">
    <div align="center">
        <h1>Branch Form</h1>
        <f:form action="saveBranch" method="POST" modelAttribute="branch">

        <table>
            <c:if test="${hasError}">
                <tr>
                    <td>Errors</td>
                    <td><f:errors path="*"></f:errors></td>
                </tr>
            </c:if>
            <tr>
                <td>ID</td>
                <td><f:input path="branchId"/></td>
            </tr>
            <tr>
                <td>NAME</td>
                <td><f:input path="branchName"/></td>
            </tr>

            <tr>
                <td><b>BRANCH ADDRESS</b></td>
            </tr>
            <tr>
                <td>ADDRESS LINE1</td>
                <td><f:input path="address.addressLine1"/></td>
            </tr>
            <tr>
                <td>ADDRESS LINE2</td>
                <td><f:input path="address.addressLine2"/></td>
            </tr>
            <tr>
                <td>CITY</td>
                <td><f:input path="address.city"/></td>
            </tr>
            <tr>
                <td>STATE</td>
                <td><f:input path="address.state"/></td>
            </tr>
            <tr>
                <td>COUNTRY</td>
                <td><f:input path="address.country"/></td>
            </tr>
            <tr>
                <td>ZIP CODE</td>
                <td><f:input path="address.zipcode"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input class="btn btn-primary" type="submit" value="submit"/> </td>
            </tr>
        </table>
        </f:form>
    </div>
    </s:authorize>
    <div class="container-sm" align="center">
        <h2>Branch Record</h2>
        <table class="table table-dark table-striped">
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>ADDRESS</th>
                <s:authorize access="hasAuthority('Admin')">
                <th colspan="2">ACTION</th>
                </s:authorize>
            </tr>


                <c:forEach items="${branches}" var="branch">
            <tr>
                    <td>${branch.getBranchId()}</td>
                    <td>${branch.getBranchName()}</td>
                    <td>${branch.getAddress().getAddressLine1()} ${branch.getAddress().getAddressLine2()},
                            ${branch.getAddress().getCity()}, ${branch.getAddress().getState()}, ${branch.getAddress().getZipcode()}, ${branch.getAddress().getCountry()}
                    </td>
                    <s:authorize access="hasAuthority('Admin')">
                    <td><a href="updateBranch?branchId=${branch.getBranchId()}">Update</a> </td>
                    <td><a href="deleteBranch?branchId=${branch.getBranchId()}">Delete</a> </td>
                    </s:authorize>
            <tr>
                </c:forEach>


        </table>
    </div>
</body>
