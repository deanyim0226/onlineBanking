<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>Branch Form</title>

</head>
<body>

<!--
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotEmpty
    private String customerName;

    @Enumerated
    private Gender gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate customerDOB;

    private String customerMobileNo;

    @Embedded
    private Address customerAddress;

    private String customerRealId;

    @OneToMany(mappedBy = "accountCustomer")
    private List<Account> customerAccounts = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "userId")
    private User user;
-->
<div align="center">

    <h1>Customer Form</h1>
    <f:form action="saveCustomer" method="POST" modelAttribute="customer">
    <table>
        <tr>
            <td>ID</td>
            <td><f:input path="customerId"></f:input></td>
        </tr>
        <tr>
            <td>NAME</td>
            <td><f:input path="customerName"></f:input></td>
        </tr>
        <tr>
            <td>GENDER</td>
            <td><f:input path="gender"></f:input></td>
        </tr>
        <tr>
            <td>DOB</td>
            <td><f:input path="customerDOB"></f:input></td>
        </tr>
        <tr>
            <td>MOBILE NO</td>
            <td><f:input path="customerMobileNo"></f:input></td>
        </tr>
        <tr>
            <td>REAL ID</td>
            <td><f:input path="customerRealId"></f:input></td>
        </tr>
        <tr>
            <td><b>CUSTOMER ADDRESS</b></td>
        </tr>
        <tr>
            <td>ADDRESS LINE1</td>
            <td><f:input path="customerAddress.addressLine1"/></td>
        </tr>
        <tr>
            <td>ADDRESS LINE2</td>
            <td><f:input path="customerAddress.addressLine2"/></td>
        </tr>
        <tr>
            <td>CITY</td>
            <td><f:input path="customerAddress.city"/></td>
        </tr>
        <tr>
            <td>STATE</td>
            <td><f:input path="customerAddress.state"/></td>
        </tr>
        <tr>
            <td>COUNTRY</td>
            <td><f:input path="customerAddress.country"/></td>
        </tr>
        <tr>
            <td>ZIP CODE</td>
            <td><f:input path="customerAddress.zipcode"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input class="btn btn-primary" type="submit" value="submit"/> </td>
        </tr>
    </table>
    </f:form>
</div>

<div align="center">

    <table>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>GENDER</th>
            <th>DOB</th>
            <th>MOBILE NO</th>
            <th>REAL ID</th>
            <th>ADDRESS</th>
            <th colspan="2">ACTION</th>
        </tr>

        <tr>
            <c:forEach items="${customers}" var="customer">
                <td>${customer.getCustomerId()}</td>
                <td>${customer.getCustomerName()}</td>
                <!--GENDER-->
                <td>${customer.getCustomeDOB()}</td>
                <td>${customer.getCustomerMobileNo()}</td>
                <td>${customer.getCustomerRealId()}</td>
                <!--CUSTOMER ADDRESS-->
                <!--List<Account> customerAccounts -->
                <!--User user-->
            </c:forEach>
        </tr>
    </table>

</div>

</body>
