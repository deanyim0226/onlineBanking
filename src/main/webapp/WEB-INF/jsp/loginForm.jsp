<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="s"%>


<!DOCTYPE html>
<html>
<head>
    <title>Login Form</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
    <style>

        body{
            background-image: url("https://www.cpomagazine.com/wp-content/uploads/2023/02/what-u-s-companies-can-learn-from-the-european-payment-scene_1500.jpg");

        }
        label{
            margin-top: .2em;
        }
        h1, td{
            text-align: center;
        }
        div{
            display: flex;
            flex-direction: column;
            position: absolute;

            top: 8rem;
            left: 3rem;
            right:0;
            bottom:0;

            width: 500px;
            height: 375px;

            padding: 2em;
            margin-top: 3.5em;

            margin-left: auto;
            margin-right: auto;

            background-color: #f0f0f5;
            border: 1px solid #ebebeb;
            box-shadow: 0px 1px 20px 1px rgba(0,0,0,0.8);
        }

        input{
            display: block;
            box-sizing: border-box;
            width: 27em;
            outline: none;
            height: 40px;
            line-height: 30px;
            font-size: 1em;
            text-indent: 1em;
        }
        button{
            display: block;
            background-color: black;
            color: #fff;
            font-weight: bold;
            border-radius: 10px;
            width: 19em;
            padding: 0.5em;
            font-size: 18px;

            position: relative;
            display: inline-block;
            text-align: center;
            height: 2.8em;
            cursor: pointer;
        :hover{
            color: #525252;
        }

    </style>
</head>
<body>


<div >
    <h1>Sign In</h1>
    <table>
        <tr class="alert alert-info" >
            <td>${message}</td>
        </tr>
    </table>

    <form action="login" method="POST">

        <label>
            <b>User Name</b>
            <input type="text" name="username"/>
        </label>
        <label>
            <b>Password</b>
            <input type="password" name="password"/>
        </label>

        <br>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit" value="submit" > Continue</button>

    </form>
</div>
</body>
</html>