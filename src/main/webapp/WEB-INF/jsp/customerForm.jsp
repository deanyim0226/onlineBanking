<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>Customer Form</title>

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

    <h1>Customer Form</h1>
    <f:form action="saveCustomer" method="POST" modelAttribute="customer">
    <table>
        <c:if test="${hasError}">

            <tr>
                <td>Errors</td>
                <td><f:errors path="*"></f:errors></td>
            </tr>

        </c:if>
        <tr>
            <td>USER ID</td>
            <td><f:input path="user.userId"></f:input></td>
        </tr>
        <tr>
            <td>ID</td>
            <td><f:input path="customerId"></f:input></td>
        </tr>
        <tr>
            <td>NAME</td>
            <td><f:input path="customerName"></f:input></td>
        </tr>
        <tr>
            <td>GENDER</td>
            <td>
                <c:forEach items="${genders}" var="g">
                    <c:if test="${selectedGender == g}">
                        <f:checkbox path="gender" label="${g}" value="${g}" checked="checked"></f:checkbox>
                    </c:if>
                    <c:if test="${selectedGender != g}">
                        <f:checkbox path="gender" label="${g}" value="${g}"></f:checkbox>
                    </c:if>
            </c:forEach>
            </td>
        </tr>
        <tr>
            <td>DOB</td>
            <td><f:input path="customerDOB" type="date"></f:input></td>
        </tr>
        <tr>
            <td>MOBILE NO</td>
            <td><f:input path="customerMobileNo"></f:input></td>
        </tr>
        <tr>
            <td>REAL ID</td>
            <td><f:input path="customerRealId"></f:input></td>
        </tr>
        <tr>
            <td><b>CUSTOMER ADDRESS</b></td>
        </tr>
        <tr>
            <td>ADDRESS LINE1</td>
            <td><f:input path="customerAddress.addressLine1"/></td>
        </tr>
        <tr>
            <td>ADDRESS LINE2</td>
            <td><f:input path="customerAddress.addressLine2"/></td>
        </tr>
        <tr>
            <td>CITY</td>
            <td><f:input path="customerAddress.city"/></td>
        </tr>
        <tr>
            <td>STATE</td>
            <td><f:input path="customerAddress.state"/></td>
        </tr>
        <tr>
            <td>COUNTRY</td>
            <td><f:input path="customerAddress.country"/></td>
        </tr>
        <tr>
            <td>ZIP CODE</td>
            <td><f:input path="customerAddress.zipcode"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input class="btn btn-primary" type="submit" value="submit"/> </td>
        </tr>
    </table>
    </f:form>
</div>

<div class="container-sm" align="center">
    <h2>Customer Record</h2>
    <table class="table table-dark table-striped" >
        <tr>

            <th>USER ID</th>
                <s:authorize access="hasAuthority('Admin')">
                <th>ID</th>
                </s:authorize>
            <th>NAME</th>
            <th>GENDER</th>
            <th>DOB</th>
            <th>MOBILE NO</th>
            <th>REAL ID</th>
            <th>ADDRESS</th>
            <th colspan="2">ACTION</th>
        </tr>


            <c:forEach items="${customers}" var="customer">
        <tr>

                <td>${customer.getUser().getUserId()}</td>
            <s:authorize access="hasAuthority('Admin')">
                <td>${customer.getCustomerId()}</td>
            </s:authorize>
                <td>${customer.getCustomerName()}</td>
                <td>${customer.getGender()}</td>
                <td>${customer.getCustomerDOB()}</td>
                <td>${customer.getCustomerMobileNo()}</td>
                <td>${customer.getCustomerRealId()}</td>
                <td>
                        ${customer.getCustomerAddress().getAddressLine1()} ${customer.getCustomerAddress().getAddressLine2()},
                        ${customer.getCustomerAddress().getCity()}, ${customer.getCustomerAddress().getState()}, ${customer.getCustomerAddress().getZipcode()}, ${customer.getCustomerAddress().getCountry()}
                </td>

                <td><a href="updateCustomer?customerId=${customer.getCustomerId()}">Update</a> </td>
            <s:authorize access="hasAuthority('Admin')">
                <td><a href="deleteCustomer?customerId=${customer.getCustomerId()}">Delete</a> </td>
                <!--List<Account> customerAccounts -->
                <!--User user-->
            </s:authorize>
        </tr>
            </c:forEach>

    </table>

</div>

</body>
