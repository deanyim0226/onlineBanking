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

            $("#addBranch").click(function(){

                $("#modal_branchId").val("")
                $("#modal_branchName").val("")

                $("#modal_address1").val("")
                $("#modal_address2").val("")
                $("#modal_city").val("")
                $("#modal_state").val("")
                $("#modal_zipcode").val("")
                $("#modal_country").val("")

                $("#modal_branchId").attr("readonly",false)
                $("#myModal").toggle()
            })


            $(".update-admin").each(function(index,element){

                $(element).click(function(){

                    let branchId = $(element).attr("data-branch-id")

                    $.ajax({
                        type:"GET",
                        url:"http://localhost:9100/r/getBranch/" + branchId,
                        success: function(response){

                            $("#modal_branchId").val(response.branchId)
                            $("#modal_branchName").val(response.branchName)

                            $("#modal_address1").val(response.address.addressLine1)
                            $("#modal_address2").val(response.address.addressLine2)
                            $("#modal_city").val(response.address.city)
                            $("#modal_state").val(response.address.state)
                            $("#modal_zipcode").val(response.address.zipcode)
                            $("#modal_country").val(response.address.country)

                        },
                        error: function(err){
                            alert("error while retrieving branch " + err)
                        }
                    })

                    $("#modal_branchId").attr("readonly",true)
                    $("#myModal").toggle()

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
<!--
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
    -->
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
                    <f:form action="saveBranch" method="POST" modelAttribute="branch">
                        <div class="col">
                            <c:if test="${hasError}">
                                <tr>
                                    <td>Errors</td>
                                    <td><f:errors path="*"></f:errors></td>
                                </tr>
                            </c:if>

                            BRANCH ID <f:input path="branchId" class="form-control" type="number"  id="modal_branchId"/>
                            BRANCH NAME <f:input path="branchName" class="form-control" type="text"  id="modal_branchName"/>

                            <br>
                            <div class="row g-3">

                                <div class="col-12">
                                    <h5 class="text-center">BRANCH ADDRESS</h5>
                                </div>

                                <div class="col-12">
                                    ADDRESS LINE1 <f:input class="form-control" path="address.addressLine1" id="modal_address1" />
                                </div>
                                <div class="col-12">
                                    ADDRESS LINE2 <f:input class="form-control" path="address.addressLine2"  id="modal_address2"/>
                                </div>
                                <div class="col-md-6">
                                    CITY <f:input class="form-control" path="address.city"  id="modal_city"/>
                                </div>
                                <div class="col-md-6">
                                    STATE <f:input class="form-control" path="address.state"  id="modal_state"/>
                                </div>
                                <div class="col-md-6">
                                    ZIP CODE <f:input class="form-control" path="address.zipcode" id="modal_zipcode"/>
                                </div>
                                <div class="col-md-6">
                                    COUNTRY <f:input class="form-control" path="address.country" id="modal_country"/>
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
        <h2>Branch Record</h2>

        <table class="table table-dark table-striped">
            <tr>
                <th>BRANCH-ID</th>
                <th>NAME</th>
                <th>ADDRESS</th>
                <s:authorize access="hasAuthority('Admin')">
                <th colspan="2">ACTION</th>
                <th><button class="btn btn-success" id="addBranch">ADD</button></th>
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
                    <td><a class="update-admin" data-branch-id="${branch.getBranchId()}" href="updateBranch?branchId=${branch.getBranchId()}">Update</a> </td>
                    <td><a href="deleteBranch?branchId=${branch.getBranchId()}">Delete</a> </td>
                </s:authorize>
            <tr>
                </c:forEach>


        </table>
    </div>
</body>
