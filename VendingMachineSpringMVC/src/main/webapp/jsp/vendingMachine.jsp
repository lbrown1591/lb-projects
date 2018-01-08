<%-- 
    Document   : vendingMachine
    Created on : Aug 2, 2017, 12:27:32 AM
    Author     : softwareguild
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vending Machine</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/myStyle.css">
    </head>
    <body>
        <div class="container">
            <div class="normal">
                <h1 align="center">Vending Machine</h1>
                <hr/>
            </div>
            <div class="content-col1">

                <ul class="horizontal" id="itemList">
                    <c:forEach var="currentItem" items="${itemList}">
                        <li>
                            <form action="http://localhost:8080/VendingMachineSpringMVC/selectItem">
                                <input type="hidden" name="itemKey" value="${currentItem.itemKey}">
                                <button class="square" type="submit">
                                    <c:out value="${currentItem.itemKey}"/>
                                    <br><br>
                                    <c:out value="${currentItem.name}"/>
                                    <br><br>
                                    $<c:out value="${currentItem.cost}"/>
                                    <br><br><br>
                                    Quantity Left: <c:out value="${currentItem.numOfItems}"/>
                                </button>
                            </form>
                        </li>
                    </c:forEach>
                </ul>

            </div>

            <div class="content-col2">
                <div>
                    <h2>Total $ In</h2> 

                    <br>
                    <form role="form" id="add-money-form">
                        <input type="text" id="total-money" value="${balance}"readonly/>
                    </form>
                    <br>
                    <form style="display:inline;" action="http://localhost:8080/VendingMachineSpringMVC/addFunds">
                        <input type="hidden" name="money" value="1">
                        <input type="submit" value="Add Dollar">   
                    </form>

                    <form style="display:inline;" action="http://localhost:8080/VendingMachineSpringMVC/addFunds">
                        <input type="hidden" name="money" value=".25">
                        <input type="submit" value="Add Quarter">   
                    </form>
                    
                    <br>

                    <form style="display:inline;" action="http://localhost:8080/VendingMachineSpringMVC/addFunds">
                        <input type="hidden" name="money" value=".1">
                        <input type="submit" value="Add Dime">   
                    </form>

                    <form style="display:inline;" action="http://localhost:8080/VendingMachineSpringMVC/addFunds">
                        <input type="hidden" name="money" value=".05">
                        <input type="submit" value="Add Nickel">   
                    </form>

                </div>
                <hr>
                <div>
                  
                    <form role="form" id="pick-item-form">
                        <h2>Messages</h2>
                        <input type="text" id="messages" value="${message}" readonly/>
                        <br><br>
                    </form>

                    <form>
                        <label for="select-item">Item: </label>
                        <input type="text" id="select-item"  value="${itemSelected}"readonly="readonly"/>
                    </form>
                    <br> <br>

                    <form action="http://localhost:8080/VendingMachineSpringMVC/purchaseItem">
                        <input type="submit" value="Make Purchase">   
                    </form>
                </div>

                <hr>

                <div>
                    <form role="form" id="change-form">
                        <h2>Change</h2>
                        <input type="text" id="change-due" value="${changeDue.toString()}" readonly/>
                    </form>
                    
                     <form action="http://localhost:8080/VendingMachineSpringMVC/getChange">
                        <input type="submit" value="Change Return">   
                    </form>

                </div>

            </div>

            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
