<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Location Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Edit Locations</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sightings">Sightings</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superHeros">Super Heros</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/locations">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organizations">Organizations</a></li>
                </ul>    
            </div>

            <div class="row">
                <!-- 
                    Add a col to hold the summary table - have it take up half the row 
                -->
                <div class="col-md-4">
                    
                    <img src="css/pics/tempMap.png">
                                    
                </div> <!-- End col div -->
                <!-- 
                    Add col to hold the new loc form - have it take up the other 
                    half of the row
                -->
                <div class="col-md-8">
                    <h2 class="col-md-offset-2">Edit Location</h2>
                    <sf:form class="form-horizontal" 
                          role="form" method="POST" 
                          action="editLocation"
                          modelAttribute="location">
                        <div class="form-group">
                            <label for="add-location-name" class="col-md-4 control-label">Location Name:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="locName" placeholder="Location Name"/>
                                <sf:errors path="locName" cssClass="error"></sf:errors>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-description" class="col-md-4 control-label">Description:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="description" placeholder="Description"/>
                                <sf:errors path="description" cssClass="error"></sf:errors>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-street" class="col-md-4 control-label">Street Address:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="street" placeholder="Street Address"/>
                                <sf:errors path="street" cssClass="error"></sf:errors>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-city" class="col-md-4 control-label">City:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="city" placeholder="City"/>
                                <sf:errors path="city" cssClass="error"></sf:errors>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-state" class="col-md-4 control-label">State:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="state" placeholder="State"/>
                                <sf:errors path="state" cssClass="error"></sf:errors>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-zip" class="col-md-4 control-label">Zip Code:</label>
                            <div class="col-md-8">
                                <sf:input type="number" class="form-control" path="zipCode" placeholder="Zip Code"/>
                                <sf:errors path="zipCode" cssClass="error"></sf:errors>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-country" class="col-md-4 control-label">Country:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="country" placeholder="Country"/>
                                <sf:errors path="country" cssClass="error"></sf:errors>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-latitude" class="col-md-4 control-label">Latitude:</label>
                            <div class="col-md-8">
                                <sf:input type="number" class="form-control" path="coordinate.latitude" placeholder="Latitude"/>
                                <sf:errors path="coordinate.latitude" cssClass="error"></sf:errors>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-longitude" class="col-md-4 control-label">Longitude:</label>
                            <div class="col-md-8">
                                <sf:input type="number" class="form-control" path="coordinate.longitude" placeholder="Longitude"/>
                                <sf:errors path="coordinate.longitude" cssClass="error"></sf:errors>
                                <sf:hidden path="locationID"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Edit Location"/>
                            </div>
                        </div>
                    </sf:form>

                </div> <!-- End col div -->

            </div> <!-- End row div -->        

        </div>




        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>


