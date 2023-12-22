<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>ATM</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            $("#accountFrom").prop('disabled',true)

            $("#transactionType").on("change",function (){

                if($(this).val() === "DEPOSIT"){
                    $("#accountFrom").prop('disabled',true)
                    $("#accountTo").prop('disabled',false)

                }else if($(this).val() === "WITHDRAWAL"){
                    $("#accountTo").prop('disabled',true)
                    $("#accountFrom").prop('disabled',false)

                }else{
                    $("#accountTo").prop('disabled',false)
                    $("#accountFrom").prop('disabled',false)
                }
            })

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
                <td><a href="atm">ATM</a></td>
                <s:authorize access="isAuthenticated()">
                    <td> | </td>
                    <td><a href="/logout">Logout</a></td>
                </s:authorize>
            </tr>
        </table>
    </div>

    <div align="center" >
        <h1>TRANSFER FORM</h1>

        <f:form action="saveTransaction" method="POST" modelAttribute="bankTransaction">

        <table>
            <!--
            <tr>
                <th>BankTransactionId</th>
                <td><f:input path="bankTransactionId"></f:input></td>
            </tr>
            -->
            <c:if test="${hasError}">
                <tr>
                    <td>Errors</td>
                    <td><f:errors path="*"></f:errors></td>
                </tr>
            </c:if>
            <tr>
                <th>TransactionType</th>
                <td>
                    <f:select id="transactionType" path="bankTransactionType">
                    <c:forEach items="${transactionTypes}" var="type">
                        <f:option value="${type}" />
                    </c:forEach>
                    </f:select>
            </tr>

            <tr>
                <th>From</th>
                <td>
                    <f:select id="accountFrom" path="bankTransactionFromAccount">
                        <c:forEach items="${accounts}" var="account">
                             <f:option value="${account.getAccountId()}" label=" ${account.getAccountBranch().getBranchName()} ${account.getAccountType()} ${account.getAccountId()}"></f:option>
                        </c:forEach>
                    </f:select>
                </td>
            </tr>
            <tr>
                <th>To</th>
                <td>
                <f:select id="accountTo" path="bankTransactionToAccount">
                    <c:forEach items="${accounts}" var="account">
                        <f:option value="${account.getAccountId()}" label="${account.getAccountBranch().getBranchName()} ${account.getAccountType()} ${account.getAccountId()}"></f:option>
                    </c:forEach>

                </f:select>
                </td>
            </tr>
            <tr>
                <th>Amount</th>
                <td><f:input path="bankTransactionAmount" ></f:input></td>
            </tr>

            <tr>
                <th>Comments</th>
                <td><f:input path="comments"></f:input></td>
            </tr>

            <tr>
                <td colspan="2" align="center"><input class="btn btn-primary" type="submit" value="submit"> </td>
            </tr>
        </table>
        </f:form>
    </div>

    <div class="container-sm" align="center">
        <h2>Transaction Record</h2>
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
            <c:forEach items="${transactions}" var="transaction">
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

<!--
private Long backTransactionFromAccount;

private Long backTransactionToAccount;

private double bankTransactionAmount;

@Enumerated(EnumType.STRING)
private TransactionType bankTransactionType;

private LocalDateTime bankTransactionDateTime;

private String comments;

-->