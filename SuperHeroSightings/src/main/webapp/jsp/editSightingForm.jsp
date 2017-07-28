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
            <h1>Edit Sightings</h1>
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
            <h2>Edit Sightings Page</h2>

            <div class="row">
                <!-- 
                    Add a col to hold the summary table - have it take up half the row 
                -->
                <div class="col-md-6">
                    <div class="col-md-4">

                        <img src="css/pics/tempMap.png">

                    </div>            
                </div> <!-- End col div -->
                <!-- 
                    Add col to hold the new loc form - have it take up the other 
                    half of the row
                -->
                <div class="col-md-6">
                    <h2 class="col-md-offset-1">Edit Sighting</h2>

                    <!-- !!!!! -->
                    <!-- Form code begins -->
                    <form method="post" class="form-horizontal" action="editSighting" role="form" id="myForm">
                        <!--LOCATION-->
                        <div class="form-group">
                            <label for="edit-location" class="col-md-4 control-label">Edit Locations:</label>
                            <div class="col-md-8">
                                <select name="selectedLocation" class="form-control">
                                    <c:forEach items="${locationList}" var="currentLocation" >
                                        <option value="${currentLocation.locationID}" label="${currentLocation.locName}"

                                                <c:if test="${currentLocation.locationID == preselectedLocationID}">
                                                    <c:out value="selected='selected'" />
                                                </c:if>

                                                />
                                    </c:forEach>

                                    <c:if test="${preselectedLocationID == 0}">
                                        <option  disabled="disabled" label="Choose Location!" value="notSelected"       
                                                 selected="selected"/>
                                    </c:if> 


                                </select>     

                            </div>

                        </div>
                        <!--END LOCATION-->



                        <!--START DATE -->
                        <div class="form-group"> <!-- Date input -->
                            <label class="col-md-4 control-label" for="date">Date:</label>
                            <div class="col-md-8">
                                <input class="form-control" id="date" name="date" placeholder="MM/DD/YYY" type="text" required value="${sightingDate}"/>
                            </div>
                            <p  id="verifyDate">${dateError}</p>
                        </div>
                        <!--END DATE -->


                        <!--START SUPER HERO -->
                        <div class="form-group">
                            <label for="edit-superHero" class="col-md-4 control-label">Edit Super Heros:</label>
                            <div class="col-md-8">
                                <select multiple name="selectedSuperHeros[]" class="form-control">
                                    <c:forEach items="${superHeroList}" var="currentHero" varStatus="status" >
                                        <option value="${currentHero.superHeroID}" label="${currentHero.superHeroName}"

                                                <c:if test="${preSelectedHeros[status.index] == 1}">
                                                    <c:out value="selected='selected'" />
                                                </c:if>    

                                                />
                                    </c:forEach>
                                </select>     
                            </div>
                        </div>
                        <input type="hidden" name="sightingID" value="${sightingID}"/>
                        <!--END SUPER HERO -->

                        <!--SUBmIT -->
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Edit Sighting" id="formButton"/>
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
        <script src="${pageContext.request.contextPath}/js/home.js"></script>
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


