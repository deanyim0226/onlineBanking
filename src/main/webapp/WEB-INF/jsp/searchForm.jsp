<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

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
        <h1>Search</h1>

        <f:form action="searchTransaction" method="POST" modelAttribute="search">
            <table>
                <tr>
                    <td>Search by transaction type</td>
                    <td>
                        <f:select path="transactionType">
                        <c:forEach items="${transactionTypes}" var="t">
                            <f:option value="${t}"></f:option>
                        </c:forEach>
                        </f:select>
                    </td>
                </tr>

                <tr>
                    <td>Search by keyword</td>
                    <td><f:input type="text" path="keyword"></f:input></td>
                </tr>
                <tr>
                    <td>Search by date</td>
                    <td>
                        <f:select path="periodicalType">
                            <c:forEach items="${periodicalTypes}" var="p">
                                <f:option value="${p}"></f:option>
                            </c:forEach>
                        </f:select>
                    </td>
                </tr>
                <tr>
                    <td>Search by range from</td>
                    <td><f:input type="date"  path="dateFrom"></f:input></td>
                </tr>
                <tr>
                    <td>Search by range to</td>
                    <td><f:input type="date" path="dateTo"></f:input></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input class="btn btn-primary" type="submit" value="search"/> </td>
                </tr>
            </table>
        </f:form>
    </div>

    <div class="container-sm" align="center">
        <h2>Search Result</h2>
        <table class="table table-dark table-striped">

            <tr>
                <th>ID</th>
                <th>TYPE</th>
                <th>AMOUNT</th>
                <th>FROM</th>
                <th>TO</th>
                <th>COMMENTS</th>
                <th>DATE/TIME</th>
            </tr>

            <c:forEach items="${filteredTransactions}" var="transaction">
                <tr>
                    <td>${transaction.getBankTransactionId()}</td>
                    <td>${transaction.getBankTransactionType()}</td>
                    <td>$${transaction.getBankTransactionAmount()}</td>
                    <td>${transaction.getBankTransactionFromAccount()}</td>
                    <td>${transaction.getBankTransactionToAccount()}</td>
                    <td>${transaction.getComments()}</td>
                    <td>${transaction.getBankTransactionDateTime()}</td>
                </tr>
            </c:forEach>

        </table>
    </div>
    </body>
</html>