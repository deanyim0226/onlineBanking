<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="s"%>

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
            margin-top: 2em;
            margin-bottom: 1em;
            color: honeydew;
        }

    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){

            $(".update").each(function(index, element){

                $(element).click(function(){
                    event.preventDefault();
                    let userId = $(element).attr("data-user-id")
                    $.ajax({
                        type: "GET",
                        url: "http://localhost:9100/r/getUser/" + userId,
                        success: function (response) {
                            $("#modal_userId").val(response.userId)
                            $("#modal_email").val(response.email)
                            $("#modal_username").val(response.username)
                            $("#modal_password").val("")

                            let roles = response.roles
                            $(".checkbox2").each(function (index, element) {
                                for (role of roles) {
                                    if (role.roleId == $(element).val()) {
                                        $(element).attr("checked", true)
                                    }
                                }
                            })
                        },
                        error: function (err) {
                            alert("error while getting user by id")
                        }
                    })

                    $("#modal_userId").attr("readonly",true)


                    $("#myModal").toggle()
                    return false;
                })

            })

            $(".update-admin").each(function(index, element){

                $(element).click(function(){
                    event.preventDefault();
                    let userId = $(element).attr("data-user-id")

                    $(".checkbox2").each(function(index,element){
                        $(element).attr("checked",false)
                    })

                    $.ajax({
                        type:"GET",
                        url:"http://localhost:9100/r/getUser/"+userId,
                        success: function(response){
                            $("#modal_userId").val(response.userId)
                            $("#modal_email").val(response.email)
                            $("#modal_username").val(response.username)
                            $("#modal_password").val(response.password)

                            let roles = response.roles

                            $(".checkbox2").each(function(index,element){
                                for(role of roles){
                                    if(role.roleId == $(element).val()){
                                        $(element).attr("checked",true)
                                    }
                                }

                            })
                        },
                        error: function(err){
                            alert("error while getting user by id")
                        }
                    })

                    $("#modal_userId").attr("readonly",true)

                    $("#myModal").toggle()
                    return false;
                })

            })

            $("#addUser").click(function(){

                $("#modal_userId").val("")
                $("#modal_email").val("")
                $("#modal_username").val("")
                $("#modal_password").val("")

                $("#modal_userId").removeAttr("readonly")

                $(".checkbox2").each(function(index, element){
                    $(element).attr("checked",false)
                })

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
                    <f:form action="saveUser" method="POST" modelAttribute="user">
                    <div class="col">
                        USER ID <f:input path="userId" class="form-control" type="number" id="modal_userId"/>
                        EMAIL <f:input path="email" class="form-control" type="text" id="modal_email"/>
                        NAME <f:input path="username" class="form-control" type="text" id="modal_username"/>
                        PASSWORD<f:input path="password" class="form-control" type="password" id="modal_password"/>

                           <c:forEach items="${roles}" var="role" >

                                <c:if test="${retrievedRole.contains(role)}">
                                    <f:checkbox class="checkbox1" path="roles" label="${role.getName()}" value="${role.getRoleId()}" checked="ture"/>
                                </c:if>

                                <c:if test="${!retrievedRole.contains(role)}">
                                    <f:checkbox class="checkbox2" path="roles" label="${role.getName()}" value="${role.getRoleId()}"/>
                                </c:if>

                            </c:forEach>

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
    <table>
        <c:if test="${hasError}">
        <tr>
            <td>Errors</td>
            <td><f:errors path="*"></f:errors></td>
        </tr>
        </c:if>
    </table>
    <div class="container-sm" align="center" >
        <h2>User Record</h2>
        <table class="table table-dark table-striped">
            <tr>
                <th>USER-ID</th>
                <th>NAME</th>
                <th>PASSWORD</th>
                <th>EMAIL</th>
                <th>ROLES</th>
                <th colspan="2">Actions</th>
                <s:authorize access="hasAuthority('Admin')">
                <th> <button class="btn btn-success" id="addUser">ADD</button></th>
                </s:authorize>
            </tr>

            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.getUserId()}</td>
                    <td>${user.getUsername()}</td>
                    <td>${user.getPassword()}</td>
                    <td>${user.getEmail()}</td>
                    <td>
                        <c:forEach items="${user.getRoles()}" var="role">
                            ${role.getName()}
                        </c:forEach>
                    </td>
                    <s:authorize access="!hasAuthority('Admin')">
                    <td><a data-user-id="${user.getUserId()}" class="update" href="updateUser?userId=${user.getUserId()}">Update</a> </td>
                        </s:authorize>
                    <s:authorize access="hasAuthority('Admin')">
                        <td><a data-user-id="${user.getUserId()}" class="update-admin" href="updateUser?userId=${user.getUserId()}">Update</a> </td>
                    </s:authorize>
                    <s:authorize access="hasAuthority('Admin')">
                        <td><a href="deleteUser?userId=${user.getUserId()}">Delete</a> </td>
                    </s:authorize>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>