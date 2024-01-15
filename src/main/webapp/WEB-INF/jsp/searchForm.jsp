<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>

        <meta charset="ISO-8859-1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

        <title>User Form</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <style>
            body{
                background-image: url("https://www.cpomagazine.com/wp-content/uploads/2023/02/what-u-s-companies-can-learn-from-the-european-payment-scene_1500.jpg");

            }
            h2{
                color: honeydew;
                margin-top: 2em;
                margin-bottom: 1em;
            }

        </style>
        <script>
            $(document).ready(function(){

                $("#search").click(function(){

                    $("#myModal").toggle()
                })

                $(".close").click(function(){
                    $("#myModal").hide()
                })

                $("#close").click(function(){
                    $("#myModal").hide()
                })
            })
        </script>
    </head>
    <body>

    <header>

        <nav class="navbar bg-dark border-bottom border-body" data-bs-theme="dark">

            <a class="btn btn-dark dropdown"   href="home"  >HOME</a>

            <ul class="nav justify-content-end">

                <li class = "nav-item"><a class="btn btn-dark dropdown"  href="userForm" >USER</a></li>
                <s:authorize access="hasAuthority('Admin')">
                    <li class = "nav-item"><a class="btn btn-dark dropdown"  href="roleForm" >ROLE</a></li>
                    <li class = "nav-item"><a class="btn btn-dark dropdown"  href="branchForm">BRANCH</a></li>
                </s:authorize>
                <li class = "nav-item"><a class="btn btn-dark dropdown"  href="customerForm">CUSTOMER</a></li>
                <li class = "nav-item"><a class="btn btn-dark dropdown"  href="accountForm">ACCOUNT</a></li>
                <li class = "nav-item"><a class="btn btn-dark dropdown"  href="atm">TRANSACTION</a></li>
                <li class = "nav-item"><a class="btn btn-dark dropdown"   href="searchForm">SEARCH </a></li>
                <s:authorize access="isAuthenticated()">
                    <li class = "nav-item"><a class="btn btn-dark dropdown"  href="/logout">LOGOUT</a></li>
                </s:authorize>
            </ul>

        </nav>

    </header>



    <div class="container-sm" align="center">
        <h2>Search Result</h2>
        <table class="table table-dark table-striped">

            <tr>
                <th>TRANSACTION-ID</th>
                <th>TYPE</th>
                <th>AMOUNT</th>
                <th>FROM</th>
                <th>TO</th>
                <th>COMMENTS</th>
                <th>DATE/TIME</th>
                <th> <button class="btn btn-success" id="search">SEARCH</button></th>

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
                    <td></td>
                </tr>
            </c:forEach>

        </table>
    </div>

    <div class="modal" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- Modal Header -->
                <div class="modal-header" >
                    <h4 class="modal-title">SEARCH TRANSACTION</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <!-- Modal body -->
                <div class="modal-body">
                    <f:form action="searchTransaction" method="POST" modelAttribute="search">
                        <div class="col">
                            <c:if test="${hasError}">
                                <tr>
                                    <td>Errors</td>
                                    <td><f:errors path="*"></f:errors></td>
                                </tr>
                            </c:if>

                            TRANSACTION TYPE
                            <f:select path="transactionType" class="form-control">
                                <c:forEach items="${transactionTypes}" var="t">
                                    <f:option value="${t}"></f:option>
                                </c:forEach>
                            </f:select>

                            DATE FROM
                            <f:input type="date"   class="form-control" path="dateFrom"/>

                            DATE TO
                            <f:input type="date" class="form-control" path="dateTo"/>

                            KEYWORD
                            <f:input type="text" class="form-control" path="keyword"/>

                            <input style="margin-top:25px" class="btn form-control btn-primary" type="submit" id="" value="submit"/>
                        </div>
                    </f:form>
                </div>
                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" id="close" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>