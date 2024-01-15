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
    <title>User Form</title>
    <link rel="stylesheet" href="../css/style.css">
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

            $("#openAccount").click(function(){

                $("#modal_customerId").attr("readonly",false)
                $("#modal_accountId").attr("readonly",false)
                $("#modal_date").attr("readonly",false)

                $("#myModal").toggle();
            })

            $(".update-admin").each(function(index,element){

                $(element).click(function(){

                    let accountId = $(element).attr("data-account-id")

                    $.ajax({
                        type:"GET",
                        url:"http://localhost:9100/r/getAccount/" + accountId,
                        success: function(response){

                            $("#modal_branchId").val(response.accountBranch.branchId)
                            $("#modal_customerId").val(response.accountCustomer.customerId)
                            $("#modal_accountId").val(response.accountId)
                            $("#modal_accountHolder").val(response.accountHolder)
                            $("#modal_accountBalance").val(response.accountBalance)
                            $("#modal_accountType").val(response.accountType)
                            $("#modal_date").val(response.accountDateOpened)

                        },
                        error: function(err){
                            alert("error while retrieving account " + err)
                        }
                    })

                    $("#modal_customerId").attr("readonly",true)
                    $("#modal_accountId").attr("readonly",true)
                    $("#modal_date").attr("readonly",true)
                    $("#myModal").toggle();
                    return false;

                })
            })


            $("#close").click(function(){
                $("#myModal").hide()
            })

            $(".close").click(function(){
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

    <div class="modal" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- Modal Header -->
                <div class="modal-header" >
                    <h4 class="modal-title">ACCOUNT INFO</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <!-- Modal body -->
                <div class="modal-body">
                    <f:form action="saveBranch" method="POST" modelAttribute="account">
                        <div class="col">
                            <c:if test="${hasError}">
                                <tr>
                                    <td>Errors</td>
                                    <td><f:errors path="*"></f:errors></td>
                                </tr>
                            </c:if>
                            BRANCH ID <f:input path="accountBranch.branchId" class="form-control" type="number" id="modal_branchId"/>
                            CUSTOMER ID <f:input path="accountCustomer.customerId" class="form-control" type="number" id="modal_customerId"/>
                            ACCOUNT ID <f:input path="accountId" class="form-control" type="number" id="modal_accountId"/>
                            ACCOUNT HOLDER <f:input path="accountHolder" class="form-control" id="modal_accountHolder" />
                            ACCOUNT BALANCE <f:input path="accountBalance" class="form-control" id="modal_accountBalance"/>
                            ACCOUNT TYPE
                            <f:select class="form-control" path="accountType" id="modal_accountType">
                                <c:forEach items="${accountTypes}" var="accountType">
                                    <f:option value="${accountType}"></f:option>
                                </c:forEach>
                            </f:select>
                            DATE OPENED <f:input path="accountDateOpened" class="form-control" type="date" id="modal_date" />
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
        <h2>Account Record</h2>
        <table class="table table-dark table-striped">
            <tr>
                <s:authorize access="hasAuthority('Admin')">
                <th>BRANCH-ID</th>
                <th>CUSTOMER-ID</th>
                </s:authorize>
                <th>ACCOUNT-ID</th>
                <th>TYPE</th>
                <th>DATE-OPENED</th>
                <th>HOLDER</th>
                <th>BALANCE</th>
                <s:authorize access="hasAuthority('Admin')">
                <th colspan="2">Action</th>
                <th> <button class="btn btn-success" id="openAccount">OPEN</button></th>
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
                    <td><a class="update-admin" data-account-id="${account.getAccountId()}" href="updateAccount?accountId=${account.getAccountId()}">Update</a> </td>
                    <td><a href="deleteAccount?accountId=${account.getAccountId()}">Delete</a> </td>
                    </s:authorize>
            </tr>
                </c:forEach>


        </table>
    </div>
</body>
</html>