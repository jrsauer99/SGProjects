<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Super Hero Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Edit Super Heros</h1>
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

                    <img src="css/pics/shero.png">

                </div> <!-- End col div -->
                <!-- 
                    Add col to hold the new loc form - have it take up the other 
                    half of the row
                -->
                <div class="col-md-8">
                    <h2 class="col-md-offset-2">Edit Super Hero</h2>
                    <sf:form class="form-horizontal" 
                             role="form" method="POST" 
                             action="editSuperHero"
                             modelAttribute="superHero">
                        <div class="form-group">
                            <label for="edit-superHero-name" class="col-md-4 control-label">Super Hero Name:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="superHeroName" placeholder="Super Hero Name"/>
                                <sf:errors path="superHeroName" cssClass="error"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-description" class="col-md-4 control-label">Description:</label>
                                <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="description" placeholder="Description"/>
                                <sf:errors path="description" cssClass="error"></sf:errors>
                                <sf:hidden path="superHeroID"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-superPower" class="col-md-4 control-label">Super Powers:</label>
                            <div class="col-md-8">
                                <select multiple name="selectedSuperPowers[]" class="form-control">
                                    <c:forEach items="${superPowerList}" var="currentPower" varStatus="status" >
                                        <option value="${currentPower.superPowerID}" label="${currentPower.superPower}"
                                                <c:if test="${preSelectedPowers[status.index] == 1}">
                                                    <c:out value="selected='selected'" />
                                                </c:if>
                                        />
                                    </c:forEach>
                                </select>   
                                
                                
                                
                                
                                
                                <%-- <c:forEach items="${superHero.superPowers}" var="currentPower">
                                    <div class="form-check">
                                        <label class="form-check-label">
                                            <input class="form-check-input" type="checkbox" name="checkedPowers" checked="checked" value="${currentPower.superPowerID}">
                                            <c:out value="${currentPower.superPower}"/> 
                                        </label>
                                    </div> 
                                </c:forEach> --%>
                                
                            </div>
                        </div>    

                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Edit Super Hero"/>
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


