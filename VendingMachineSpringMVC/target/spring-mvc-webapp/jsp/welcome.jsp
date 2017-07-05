<%-- 
    Document   : welcome
    Created on : Jun 23, 2017, 10:45:35 AM
    Author     : apprentice
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vending Machine</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"> 
        <link href="${pageContext.request.contextPath}/css/home.css?v=AUTO_INCREMENT_VERSION" rel="stylesheet"> 
    </head>
    <body>
        <div class="container">
            <!--HEADER-->
            <h1></h1>
            <h1 class="text-center">Vending Machine</h1>
            <hr/>
            <!--ITEM BUTTONS, LEFT COLUMN-->
            <div class="col-md-9" id="button-column">

                <!--DYNAMICALLY CREATE BUTTONS HERE-->
                <c:forEach var="currentItem" items="${itemList}">
                    <div class="col-md-4">
                        <button type="button" 
                                id="${currentItem.slotNum}"
                                class="btn btn-default btn-block vend-btn">  
                            <h4 class="text-left">${currentItem.slotNum}</h4>
                            <h3 class="text-center">${currentItem.itemName}</h3>
                            <h4>$${currentItem.itemCost}</h4>
                            <br>
                            <br>
                            <h4>Quantity Left: ${currentItem.numInInventory}</h4>
                        </button>
                    </div>
                </c:forEach>

                <!-- END DYNAMIC BUTTON CREATION -->
            </div>
            <!--FORM, RIGHT COLUMN-->
            <div class="col-md-3">
                <!--MONEY IN SECTION-->
                <div class="row">
                    <h2 class="text-center">Total $ In</h2> 
                    <!--DISPLAY USER MONEY FIELD -->
                    <div class="col-md-12">
                        <div class="row user-in">
                            <h4 id="total-money" class="text-center">$${userMoney}</h4>
                        </div>
                    </div>
                    <!-- ADD MONEY BUTTONS, ROW1 -->
                    <form role="form" id="add-money-form" method="post" action="addMoney">
                        <!--added hidden field-->
                        <input type="hidden" name="hiddenUserMoney" id="hiddenUserMoney" value="${userMoney}"/>
                        <input type="hidden" name="hiddenItemNum" id="hiddenItemNum" value=""/>
                        <input type="hidden" name="hiddenUserChg" id="hiddenUserChg" value="${userChange}"/>
                        <input type="hidden" name="changeAvailMon" id="changeAvailMon" value="${changeAvail}"/>
                        <!--end added-->
                        <div class="row" style="text-align:center">
                            <div  style="display: inline-block">
                                <button type="submit"
                                        id="add-dollar-button"
                                        name="addMoney"
                                        value="1"
                                        class="btn btn-default add-money-btn">
                                    Add Dollar
                                </button>
                            </div>
                            <div  style="display: inline-block">
                                <button type="submit"
                                        id="add-quarter-button"
                                        name="addMoney"
                                        value=".25"
                                        class="btn btn-default add-money-btn">
                                    Add Quarter
                                </button>
                            </div>
                        </div>
                        <!-- ADD MONEY BUTTONS, ROW2 -->
                        <div class="row" style="text-align:center">
                            <div  style="display: inline-block">
                                <button type="submit"
                                        id="add-dime-button"
                                        name="addMoney"
                                        value=".1"
                                        class="btn btn-default add-money-btn">
                                    Add Dime
                                </button>
                            </div>
                            <div  style="display: inline-block">
                                <button type="submit"
                                        id="add-nickel-button"
                                        name="addMoney"
                                        value=".05"
                                        class="btn btn-default add-money-btn">
                                    Add Nickel
                                </button>
                            </div>
                        </div>
                    </form>
                </div>


                <!--MESSAGES/PURCHASE SECTION-->
                <div class="row">
                    <h2 class="text-center">Messages</h2> 
                    <!--DISPLAY MESSAGES-->
                    <div class="col-md-12 messages-out">
                        <p class="text-center" id="message-out">${userMessage}</p>
                    </div>
                    <!-- SUBMIT FORM-->
                    <form role="form" id="make-purchase-form" method="post" action="purchase">
                        <input type="hidden" name="hiddenUserMoneyPur" id="hiddenUserMoneyPur" value="${userMoney}"/>
                        <input type="hidden" name="hiddenUserChgPur" id="hiddenUserChgPur" value="${userChange}"/>
                        <input type="hidden" name="changeAvailPur" id="changeAvailPur" value="${changeAvail}"/>
                        <!--ITEM INPUT-->
                        <div class="row">
                            <div class="form-group col-md-8 col-md-offset-2">
                                <label for="item-input" class="col-form-label">Item:</label>
                                <input class="form-control" type="text" id="item-input" name="itemNum" value="${itemNum}" placeholder="Item" readonly/>
                            </div>
                        </div>
                        <!-- MAKE PURCHASE BUTTON -->
                        <div class="row">
                            <div class="form-group col-md-8 col-md-offset-2">
                                <button type="submit"
                                        id="make-purchase-button"
                                        class="btn btn-default btn-block">Make Purchase
                                </button>
                            </div> 
                        </div>
                    </form>  
                </div>

                <!--CHANGE SECTION-->
                <div class="row">
                    <h2 class="text-center">Change</h2> 
                    <!--CHANGE MESSAGES-->
                    <div class="col-md-12 change-messages-out">
                        <p class="text-center return-change-message" id="change-out" style="visibility: visible">
                            <c:choose>
                                <c:when test="${userChange.numQuarters>0}">
                                    <c:out value="QUARTERS: ${userChange.numQuarters}" />
                                    <br/>
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose> 

                            <c:choose>
                                <c:when test="${userChange.numDimes>0}">
                                    <c:out value="DIMES: ${userChange.numDimes}" />
                                    <br/>
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${userChange.numNickels>0}">
                                    <c:out value="NICKELS: ${userChange.numNickels}" />
                                    <br/>
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${userChange.numPennies>0}">
                                    <c:out value="PENNIES: ${userChange.numPennies}" />
                                    <br/>
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                    <form role="form" id="change-form" action="getChange" method="post">
                        <input type="hidden" name="hiddenUserMoneyChg" id="hiddenUserMoneyChg" value="${userMoney}"/>
                        <input type="hidden" name="hiddenUserChange" id="hiddenUserChange" value="${userChange}"/>
                        <input type="hidden" name="changeAvail" id="changeAvail" value="${changeAvail}"/>
                        <!--RETURN CHANGE BUTTON -->
                        <div class="row">
                            <div class="form-group col-md-8 col-md-offset-2">
                                <button type="submit"
                                        id="return-change-button"
                                        class="btn btn-default btn-block">Change Return
                                </button>
                            </div> 
                        </div>
                    </form>     

                </div>

            </div>


            <!-- Placed at the end of the document so the pages load faster -->
            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>                  
            <script src="${pageContext.request.contextPath}/js/home.js?v=AUTO_INCREMENT_VERSION"></script>
    </body>

</html>
