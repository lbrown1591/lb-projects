<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index Page</title>
        <!-- Bootstrap core CSS -->
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
                <form action="http://localhost:8080/VendingMachineSpringMVC/displayVendingMachine">
                    <input type="submit"
                       value="See Vending Machine"/>
                </form>
                
                
                <ul class="horizontal" id="itemList">
                    
                </ul>

            </div>

            <div class="content-col2">
                <div>
                    <h2>Total $ In</h2> 

                    <br>
                    <form role="form" id="add-money-form">
                        <input type="text" id="total-money" readonly/>

                        <br>
                        <button type="button"
                                value="1"
                                id="add-dollar-button"
                                class="btn btn-default">
                            Add Dollar
                        </button>

                        <button type="button"
                                value=".25"
                                id="add-quarter-button"
                                class="btn btn-default">
                            Add Quarter
                        </button>
                        <br>

                        <button type="button"
                                value=".1"
                                id="add-dime-button"
                                class="btn btn-default">
                            Add Dime
                        </button>

                        <button type="button"
                                id="add-nickel-button"
                                value=".05"
                                class="btn btn-default">
                            Add Nickel
                        </button>


                    </form>
                </div>
                <hr>
                <div>
                    <form role="form" id="pick-item-form">
                        <h2>Messages</h2>
                        <input type="text" id="messages" readonly/>
                        <br><br>

                        <label for="select-item">Item: </label>

                        <input type="text" id="select-item"/>

                        <br> <br>
                        <button type="button"
                                id="make-purchase-button"
                                class="btn btn-default">
                            Make Purchase
                        </button>


                    </form>
                </div>

                <hr>

                <div>
                    <form role="form" id="change-form">
                        <h2>Change</h2>
                        <input type="text" id="change-due" readonly/>
                    </form>

                </div>

            </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

