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
    <style>
        body{
            background-image: url("https://www.cpomagazine.com/wp-content/uploads/2023/02/what-u-s-companies-can-learn-from-the-european-payment-scene_1500.jpg");

        }
        h2{
            color: honeydew;
            margin-top: 2em;
            margin-bottom: 1em;
        }

        .modal-dialog{
            overflow-y: initial !important
        }
        .modal-body{
            height: 500px;
            overflow-y: auto;
        }

    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            $(".close").click(function(){
                $("#myModal").hide()
            })

            $("#close").click(function(){
                $("#myModal").hide()
            })

            $(".update").each(function(index,element){

                $(element).click(function(){

                    let customerId = $(element).attr("data-customer-id")
                    $("#modal_userId").attr("readonly",true)
                    $("#modal_customerId").attr("readonly",true)
                    $("#modal_customerGender").attr("readonly",true)
                    $("#modal_customerDOB").attr("readonly",true)

                    $.ajax({
                        type:"GET",
                        url:"http://localhost:9100/r/getCustomer/" + customerId,
                        success: function(response){

                            $("#modal_userId").val(response.user.userId)
                            $("#modal_customerId").val(response.customerId)
                            $("#modal_customerName").val(response.customerName)
                            $("#modal_customerMobile").val(response.customerMobileNo)
                            $("#modal_customerRealId").val(response.customerRealId)
                            $("#modal_customerDOB").val(response.customerDOB)
                            $("#modal_customerGender").val(response.gender)

                            $("#modal_address1").val(response.customerAddress.addressLine1)
                            $("#modal_address2").val(response.customerAddress.addressLine2)
                            $("#modal_city").val(response.customerAddress.city)
                            $("#modal_state").val(response.customerAddress.state)
                            $("#modal_zipcode").val(response.customerAddress.zipcode)
                            $("#modal_country").val(response.customerAddress.country)
                        },
                        error: function(err){
                            alert("Error while retrieving customer " + err)
                        }
                    })

                    $("#myModal").toggle()
                    return false;
                })
            })

            $("#addCustomer").click(function(){

                $("#modal_userId").attr("readonly",false)
                $("#modal_customerId").attr("readonly",false)
                $("#modal_customerGender").attr("readonly",false)
                $("#modal_customerDOB").attr("readonly",false)

                $("#modal_userId").val("")
                $("#modal_customerId").val("")
                $("#modal_customerName").val("")
                $("#modal_customerMobile").val("")
                $("#modal_customerRealId").val("")
                $("#modal_customerDOB").val("")
                $("#modal_customerGender").val("")

                $("#modal_address1").val("")
                $("#modal_address2").val("")
                $("#modal_city").val("")
                $("#modal_state").val("")
                $("#modal_zipcode").val("")
                $("#modal_country").val("")

                $("#myModal").toggle()
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
                <h4 class="modal-title">USER INFO</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <!-- Modal body -->
            <div class="modal-body">
                <f:form action="saveCustomer" method="POST" modelAttribute="customer" >
                    <div class="col">

                        USER-ID <f:input path="user.userId" class="form-control" type="number"  id="modal_userId"/>
                        CUSTOMER-ID <f:input path="customerId" class="form-control" type="number"  id="modal_customerId"/>
                        CUSTOMER-NAME <f:input path="customerName" class="form-control" type="text" id="modal_customerName"/>
                        MOBILE-NO <f:input path="customerMobileNo" class="form-control" type="text" id="modal_customerMobile"/>
                        REAL-ID <f:input path="customerRealId" class="form-control" type="text" id="modal_customerRealId"/>
                        GENDER
                        <f:select class="form-control" path="gender" id="modal_customerGender">
                            <c:forEach items="${genders}" var="gender">
                                <f:option value="${gender}"></f:option>
                            </c:forEach>
                        </f:select>
                        DOB <f:input path="customerDOB" class="form-control" type="date" id="modal_customerDOB"/>

                        <div class="row g-3">

                            <div class="col-12">
                                <h5 class="text-center">CUSTOMER ADDRESS</h5>
                            </div>

                            <div class="col-12">
                                ADDRESS LINE1 <f:input class="form-control" path="customerAddress.addressLine1" id="modal_address1" />
                            </div>
                            <div class="col-12">
                                ADDRESS LINE2 <f:input class="form-control" path="customerAddress.addressLine2"  id="modal_address2"/>
                            </div>
                            <div class="col-md-6">
                                CITY <f:input class="form-control" path="customerAddress.city"  id="modal_city"/>
                            </div>
                            <div class="col-md-6">
                                STATE <f:input class="form-control" path="customerAddress.state"  id="modal_state"/>
                            </div>
                            <div class="col-md-6">
                                ZIP CODE <f:input class="form-control" path="customerAddress.zipcode" id="modal_zipcode"/>
                            </div>
                            <div class="col-md-6">
                                COUNTRY <f:input class="form-control" path="customerAddress.country" id="modal_country"/>
                            </div>
                        </div>
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
    <h2>Customer Record</h2>

    <table class="table table-dark table-striped" >
        <tr>

            <th>USER-ID</th>
                <s:authorize access="hasAuthority('Admin')">
                <th>CUSTOMER-ID</th>
                </s:authorize>
            <th>NAME</th>
            <th>GENDER</th>
            <th>DOB</th>
            <th>MOBILE-NO</th>
            <th>REAL-ID</th>
            <th>ADDRESS</th>
            <th colspan="2">ACTION</th>
            <s:authorize access="hasAuthority('Admin')">
            <th><button class="btn btn-success" id="addCustomer">ADD</button></th>
            </s:authorize>
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

                <td><a data-customer-id="${customer.getCustomerId()}" class="update" href="updateCustomer?customerId=${customer.getCustomerId()}">Update</a> </td>
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
