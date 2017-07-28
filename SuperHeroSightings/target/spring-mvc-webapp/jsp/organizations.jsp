<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
            <h1>Organizations</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sightings">Sightings</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superHeros">Super Heros</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/locations">Locations</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/organizations">Organizations</a></li>
                </ul>    
            </div>

            <!-- ***************************************** -->
            <div class="row">
                <!-- 
                    Add a col to hold the summary table - have it take up half the row 
                -->
                <div class="col-md-6">
                    <h2>Organizations</h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="30%">Organization Name</th>
                            <th width="60%">Description</th>
                            <th width="5%"></th>
                            <th width="5%"></th>
                        </tr>
                        <c:forEach var="currentOrg" items="${orgList}">
                            <tr>
                                <td>
                                    <a href="displayOrganizationDetails?organizationID=${currentOrg.organizationID}">
                                        <c:out value="${currentOrg.orgName}"/> 
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentOrg.description}"/>
                                </td>
                                <td>
                                    <a href="editOrganizationForm?organizationID=${currentOrg.organizationID}&locationID=${currentOrg.location.locationID}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                </td>
                                <td>
                                    <a type="button" class="close" aria-label="Close" href="deleteOrganization?organizationID=${currentOrg.organizationID}">
                                        <span aria-hidden="true">&times;</span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div> <!-- End col div -->
                <!-- 
                    XX
                -->
                <div class="col-md-6">
                    <h2 class="col-md-offset-1">Add Organization</h2>

                    <sf:form class="form-horizontal" 
                             role="form" method="POST" 
                             action="createOrganization"
                             modelAttribute="organization">
                        <div class="form-group">
                            <label for="add-organization" class="col-md-4 control-label">Organization Name:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="orgName" placeholder="Organization Name"/>
                                <sf:errors path="orgName" cssClass="error"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-orgDesc" class="col-md-4 control-label">Description:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="description" placeholder="Description"/>
                                <sf:errors path="description" cssClass="error"></sf:errors>
                                <sf:hidden path="organizationID"/>
                            </div>
                            </div>
                            
                             <div class="form-group">
                                <label for="add-orgPhone" class="col-md-4 control-label">Phone Number:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="phoneNumber" placeholder="Phone Number"/>
                                <sf:errors path="phoneNumber" cssClass="error"></sf:errors>
                            </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="add-email" class="col-md-4 control-label">Email:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="email" placeholder="Email"/>
                                <sf:errors path="email" cssClass="error"></sf:errors>
                            </div>
                            </div>
                            
                            
                        <!--LOCATION-->
                        <div class="form-group">
                            <label for="add-location" class="col-md-4 control-label">Locations:</label>
                            <div class="col-md-8">
                                <select name="selectedLocation" class="form-control">
                                    <c:forEach items="${locationList}" var="currentLocation" >
                                        <option value="${currentLocation.locationID}" label="${currentLocation.locName}"
                                                
                                                <c:if test="${currentLocation.locationID == preselectedLocationID}">
                                                    <c:out value="selected='selected'" />
                                                </c:if>
                                                
                                                />
                                    </c:forEach>
                                </select>     

                            </div>
                        </div>
                        <!--END LOCATION-->
                        
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
                        
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Add Organization"/>
                            </div>
                        </div>
                    </sf:form> 



                </div> <!-- End col div -->

            </div> <!-- End row div -->        

        </div> <!-- End Containter -->

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

