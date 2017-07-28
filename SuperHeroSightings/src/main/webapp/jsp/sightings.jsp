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
    </head>
    <body>
        <div class="container">
            <h1>Sightings</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li role="presentation"  class="active"><a href="${pageContext.request.contextPath}/sightings">Sightings</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superHeros">Super Heros</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/locations">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organizations">Organizations</a></li>
                </ul>    
            </div>
            <h2>Sightings Page</h2>

            <div class="row">
                <!-- 
                    Add a col to hold the summary table - have it take up half the row 
                -->
                <div class="col-md-6">
                    <h2>Sightings</h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="25%">Sighting ID</th>
                            <th width="45%">Sighting Location</th>
                            <th width="20%">Sighting Date</th>
                            <th width="5%"></th>
                            <th width="5%"></th>
                        </tr>
                         <c:forEach var="currentSighting" items="${sightingList}">
                            <tr>
                                <td>
                                    <a href="displaySightingDetails?sightingID=${currentSighting.sightingID}">
                                        <c:out value="${currentSighting.sightingID}"/> 
                                    </a>
                                </td>
                                <td>
                                        <c:out value="${currentSighting.location.locName}"/> 
                                </td>
                                <td>
                                      <%-- <fmt:formatDate value="${currentSighting.sightingDate}" pattern="yyyy-MM-dd"/> --%>
                                    <c:out value="${currentSighting.sightingDate}"/>
                                </td>
                                <td>
                                    <a href="editSightingForm?sightingID=${currentSighting.sightingID}&locationID=${currentSighting.location.locationID}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                </td>
                                <td>
                                    <a type="button" class="close" aria-label="Close" href="deleteSighting?sightingID=${currentSighting.sightingID}">
                                        <span aria-hidden="true">&times;</span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div> <!-- End col div -->
                <!-- 
                    Add col to hold the new loc form - have it take up the other 
                    half of the row
                -->
                <div class="col-md-6">
                    <h2 class="col-md-offset-1">Add Sighting</h2>
                    
                    <!-- !!!!! -->
                    <!-- Form code begins -->
                    <form method="post" class="form-horizontal" action="createSighting" role="form">
                        <!--LOCATION-->
                        <div class="form-group">
                            <label for="add-location" class="col-md-4 control-label">Locations:</label>
                            <div class="col-md-8">
                                <select name="selectedLocation" class="form-control">
                                    <c:forEach items="${locationList}" var="currentLocation" >
                                        <option value="${currentLocation.locationID}" label="${currentLocation.locName}"/>
                                    </c:forEach>
                                </select>     

                            </div>
                        </div>
                        <!--END LOCATION-->
                        
                        
                        
                        <!--START DATE -->
                        <div class="form-group"> <!-- Date input -->
                            <label class="col-md-4 control-label" for="date">Date:</label>
                            <div class="col-md-8">
                            <input class="form-control" id="date" name="date" placeholder="MM/DD/YYY" type="text" required/>
                            </div>
                        </div>
                       <!--END DATE -->
                       
                       <!--START SUPER HERO -->
                       <div class="form-group">
                            <label for="add-superHero" class="col-md-4 control-label">Super Heros:</label>
                            <div class="col-md-8">
                                <select multiple name="selectedSuperHeros[]" class="form-control">
                                    <c:forEach items="${superHeroList}" var="currentHero" >
                                        <option value="${currentHero.superHeroID}" label="${currentHero.superHeroName}"/>
                                    </c:forEach>
                                </select>     
                            </div>
                        </div>
                       <!--END SUPER HERO -->
                        
                        <!--SUBmIT -->
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Add Sighting"/>
                            </div>
                        </div>
                        <!-- END SUBMIT -->
                    </form>
                    <!-- Form code ends --> 


                </div> <!-- End col div -->

            </div> <!-- End row div -->     


        </div>






        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <!-- Bootstrap Date-Picker Plugin -->
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
        <script>
                                $(document).ready(function () {
                                    var date_input = $('input[name="date"]'); //our date input has the name "date"
                                    var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
                                    var options = {
                                        format: 'mm/dd/yyyy',
                                        container: container,
                                        todayHighlight: true,
                                        autoclose: true,
                                    };
                                    date_input.datepicker(options);
                                })
        </script>

    </body>
</html>


