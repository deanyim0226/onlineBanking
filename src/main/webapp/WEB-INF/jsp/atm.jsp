<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <title>ATM</title>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){

            $("#makeTransfer").click(function(){

                $("#myModal").toggle()
            })

            $(".close").click(function(){

                $("#myModal").hide()
            })

            $("#close").click(function(){

                $("#myModal").hide()
            })

            $("#accountFrom").prop('disabled',true)

            $("#transactionType").on("change",function (){

                if($(this).val() === "DEPOSIT"){
                    $("#accountFrom").prop('disabled',true)
                    $("#accountTo").prop('disabled',false)

                }else if($(this).val() === "WITHDRAWAL"){
                    $("#accountTo").prop('disabled',true)
                    $("#accountFrom").prop('disabled',false)

                }else{
                    let accountTo = $("#accountTo").val()
                    let accountFrom = $("#accountFrom").val()

                    $("#accountTo").prop('disabled',false)
                    $("#accountFrom").prop('disabled',false)



                }
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

<div class="modal" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header" >
                <h4 class="modal-title">TRANSFER INFO</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <!-- Modal body -->
            <div class="modal-body">
                <f:form action="saveTransaction" method="POST" modelAttribute="bankTransaction">
                    <div class="col">
                        <c:if test="${hasError}">
                            <tr>
                                <td>Errors</td>
                                <td><f:errors path="*"></f:errors></td>
                            </tr>
                        </c:if>
                        TRANSACTION TYPE
                        <f:select id="transactionType" class="form-control" path="bankTransactionType">
                            <c:forEach items="${transactionTypes}" var="type">
                                <f:option value="${type}" />
                            </c:forEach>
                        </f:select>

                        FROM
                        <f:select id="accountFrom" class="form-control" path="bankTransactionFromAccount">
                            <c:forEach items="${accounts}" var="account">
                                <f:option value="${account.getAccountId()}" label=" ${account.getAccountBranch().getBranchName()} ${account.getAccountType()} ${account.getAccountId()}"></f:option>
                            </c:forEach>
                        </f:select>

                        TO
                        <f:select id="accountTo" class="form-control" path="bankTransactionToAccount">
                            <c:forEach items="${accounts}" var="account">
                                <f:option value="${account.getAccountId()}" label="${account.getAccountBranch().getBranchName()} ${account.getAccountType()} ${account.getAccountId()}"></f:option>
                            </c:forEach>
                        </f:select>

                        AMOUNT
                        <f:input class="form-control" path="bankTransactionAmount" ></f:input>

                        COMMENTS
                        <f:input path="comments" class="form-control"></f:input>

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

    <div class="container-sm" align="center">
        <h2>Transaction Record</h2>
        <table class="table table-dark table-striped">
            <tr>
                <th>TRANSACTION-ID</th>
                <th>TYPE</th>
                <th>AMOUNT</th>
                <th>FROM</th>
                <th>TO</th>
                <th>COMMENTS</th>
                <th>DATE/TIME</th>
                <th><button class="btn btn-success" id="makeTransfer">MAKE TRANSACTION</button></th>

            </tr>
            <c:forEach items="${transactions}" var="transaction">
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
</body>
</html>

<!--
private Long backTransactionFromAccount;

private Long backTransactionToAccount;

private double bankTransactionAmount;

@Enumerated(EnumType.STRING)
private TransactionType bankTransactionType;

private LocalDateTime bankTransactionDateTime;

private String comments;

-->