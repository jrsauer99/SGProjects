<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Organization Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        


    </head>
    <body>
        <div class="container">
            <h1>Edit Organization</h1>
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
                    <h2 class="col-md-offset-2">Edit Organization</h2>
                    <sf:form class="form-horizontal" 
                             role="form" method="POST" 
                             action="editOrganization"
                             modelAttribute="organization">
                        <div class="form-group">
                            <label for="edit-org-name" class="col-md-4 control-label">Organization Name:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="orgName" placeholder="Organization Name"/>
                                <sf:errors path="orgName" cssClass="error"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-description" class="col-md-4 control-label">Description:</label>
                                <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="description" placeholder="Description:"/>
                                <sf:errors path="description" cssClass="error"></sf:errors>
                                <sf:hidden path="organizationID"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="edit-phone" class="col-md-4 control-label">Phone Number:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="phoneNumber" placeholder="Phone Number:"/>
                                <sf:errors path="phoneNumber" cssClass="error"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-email" class="col-md-4 control-label">Email:</label>
                                <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="email" placeholder="Email:"/>
                                <sf:errors path="email" cssClass="error"></sf:errors>
                                </div>
                            </div>

                            <!-- START LOCATION -->
                            <!-- GOING TO NEED TO GET THIS PRESELECTED!!! -->
                            <div class="form-group">
                                <label for="edit-location" class="col-md-4 control-label">Locations:</label>
                                <div class="col-md-8">
                                    <select name="selectedLocation" class="form-control" required="required" id="mySelect">
                                    <c:forEach items="${locationList}" var="currentLocation" varStatus="status">
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
                        <!--END LOCATION -->

                        <!-- START MEMBERS -->
                        <div class="form-group">
                            <label for="edit-members" class="col-md-4 control-label">Members:</label>
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
                        <!-- END MEMBERS -->

                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Edit Organization"/>
                            </div>
                        </div>
                    </sf:form>

                </div> <!-- End col div -->

            </div> <!-- End row div -->        

        </div>




        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/home.js"></script>
    </body>
</html>
