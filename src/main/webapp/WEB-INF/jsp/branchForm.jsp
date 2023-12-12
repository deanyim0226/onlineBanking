<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>Branch Form</title>

</head>
<body>

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

    <div align="center">
        <table class="table table-dark table-striped">
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>ADDRESS</th>
                <th colspan="2">ACTION</th>
            </tr>

            <tr>
                <c:forEach items="${branches}" var="branch">
                    <td>${branch.getBranchId()}</td>
                    <td>${branch.getBranchName()}</td>
                    <td>${branch.getAddress().getAddressLine1()} ${branch.getAddress().getAddressLine2()}
                            ${branch.getAddress().getCity()} ${branch.getAddress().getState()} ${branch.getAddress().getZipcode()} ${branch.getAddress().getCountry()}
                    </td>
                    <td><a href="updateBranch?branchId=${branch.getBranchId()}">Update</a> </td>
                    <td><a href="deleteBranch?branchId=${branch.getBranchId()}">Delete</a> </td>
                </c:forEach>

            </tr>
        </table>
    </div>
</body>
