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
    <title>Role Form</title>
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

            $(".update-admin").each(function(index,element){

                $(element).click(function(){

                    $("#modal_roleId").attr("readonly",true)

                    let roleId = $(element).attr("data-role-id")

                    $.ajax({
                        type:"GET",
                        url:"http://localhost:9100/r/getRole/" + roleId,
                        success: function(response){
                            $("#modal_roleId").val(response.roleId)
                            $("#modal_roleName").val(response.name)
                        },
                        error: function (err){
                            alert("something is wrong while getting role " + err)
                        }
                    })

                    $("#myModal").toggle()
                    return false;

                })
            })

            $("#addRole").click(function(){

                $("#myModal").toggle()
                $("#modal_roleId").attr("readonly",false)
                $("#modal_roleId").val("")
                $("#modal_roleName").val("")

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

    <div class="modal" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <!-- Modal Header -->
                <div class="modal-header" >
                    <h4 class="modal-title">ROLE INFO</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <!-- Modal body -->
                <div class="modal-body">
                    <f:form action="saveRole" method="POST" modelAttribute="role">
                        <div class="col">
                            ROLE ID <f:input path="roleId" readonly="true" class="form-control" type="text" id="modal_roleId"/>
                            ROLE <f:input path="name" class="form-control" type="text" id="modal_roleName"/>
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



    <div class="container-sm" align="center" >
        <h2>Role Record</h2>

        <table class="table table-dark table-striped">
            <tr>
                <s:authorize access="hasAuthority('Admin')">
                <th>ROLE-ID</th>
                </s:authorize>
                <th>NAME</th>
                <s:authorize access="hasAuthority('Admin')">
                <th colspan="2">Action</th>
                <th> <button class="btn btn-success" id="addRole">ADD</button></th>
                </s:authorize>
            </tr>
            <c:forEach items="${roles}" var="role">
                <tr>
                    <s:authorize access="hasAuthority('Admin')">
                    <td>${role.getRoleId() }</td>
                    </s:authorize>

                    <td>${role.getName()}</td>
                    <s:authorize access="hasAuthority('Admin')">
                    <td><a data-role-id="${role.getRoleId()}" class="update-admin" href="updateRole?roleId=${role.getRoleId()}">Update</a> </td>
                    <td><a href="deleteRole?roleId=${role.getRoleId()}">Delete</a> </td>
                    </s:authorize>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>