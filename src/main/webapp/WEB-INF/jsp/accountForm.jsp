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
                <td><a href="atm">ATM</a></td>  <td> | </td>
                <td><a href="searchForm">Search</a></td>
                <s:authorize access="isAuthenticated()">
                    <td> | </td>
                    <td><a href="/logout">Logout</a></td>
                </s:authorize>
            </tr>
        </table>
    </div>
    <s:authorize access="hasAuthority('Admin')">
    <div align="center">
        <h1>Account Form</h1>
        <f:form action="saveAccount" method="POST" modelAttribute="account">
            <table>
                <c:if test="${hasError}">
                    <tr>
                        <td>Errors</td>
                        <td><f:errors path="*"></f:errors></td>
                    </tr>
                </c:if>

                <tr>
                    <td>BRANCH ID</td>
                    <td><f:input path="accountBranch.branchId"></f:input></td>
                </tr>

                <tr>
                    <td>CUSTOMER ID</td>
                    <td><f:input path="accountCustomer.customerId"></f:input></td>
                </tr>
                <tr>
                    <td>ID</td>
                    <td><f:input path="accountId"></f:input></td>
                </tr>

                <tr>
                    <td>TYPE</td>
                   
                    <td>
                        <c:forEach items="${accountTypes}" var="a">
                            <c:if test="${selectedAccountType == a}">
                                <f:radiobutton path="accountType" label="${a}" value="${a}" checked="true"></f:radiobutton>
                            </c:if>
                            <c:if test="${selectedAccountType != a}">
                                <f:radiobutton path="accountType" label="${a}" value="${a}"></f:radiobutton>
                            </c:if>

                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>DATE OPENED</td>
                    <td><f:input path="accountDateOpened" type="date" ></f:input></td>
                </tr>
                <tr>
                    <td>HOLDER</td>
                    <td><f:input path="accountHolder"></f:input></td>
                </tr>
                <tr>
                    <td>BALANCE</td>
                    <td><f:input path="accountBalance"></f:input></td>
                </tr>

                <tr>
                    <td colspan="2" align="center"><input class="btn btn-primary" type="submit" value="submit"/> </td>
                </tr>

            </table>
        </f:form>
    </div>
    </s:authorize>
    <div class="container-sm" align="center">
        <h2>Account Record</h2>
        <table class="table table-dark table-striped">
            <tr>
                <s:authorize access="hasAuthority('Admin')">
                <th>BRANCH ID</th>
                <th>CUSTOMER ID</th>
                </s:authorize>
                <th>ID</th>
                <th>TYPE</th>
                <th>DATE OPENED</th>
                <th>HOLDER</th>
                <th>BALANCE</th>
                <s:authorize access="hasAuthority('Admin')">
                <th colspan="2">Action</th>
                </s:authorize>
            </tr>



                <c:forEach items="${accounts}" var="account">
            <tr>
                <s:authorize access="hasAuthority('Admin')">
                    <td>${account.getAccountBranch().getBranchId()}</td>
                    <td>${account.getAccountCustomer().getCustomerId()}</td>
                </s:authorize>
                    <td>${account.getAccountId()}</td>
                    <td>${account.getAccountType()}</td>
                    <td>${account.getAccountDateOpened()}</td>
                    <td>${account.getAccountHolder()}</td>
                    <td>${account.getAccountBalance()}</td>
                    <s:authorize access="hasAuthority('Admin')">
                    <td><a href="updateAccount?accountId=${account.getAccountId()}">Update</a> </td>
                    <td><a href="deleteAccount?accountId=${account.getAccountId()}">Delete</a> </td>
                    </s:authorize>
            </tr>
                </c:forEach>


        </table>
    </div>
</body>
</html>