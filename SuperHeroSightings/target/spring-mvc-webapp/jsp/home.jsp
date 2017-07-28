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
        <link href="${pageContext.request.contextPath}/css/home.css?v=AUTO_INCREMENT_VERSION" rel="stylesheet"> 
    </head>
    <body>
        <div class="container">
            <h1>Super Hero Tracking System</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                	<li role="presentation" class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
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
                <div class="col-md-5">
                     <img src="css/pics/tempMap.png">
                    <h2>Summary Here</h2>       
                </div> <!-- End col div -->
                <!-- 
                    Add col to hold the new loc form - have it take up the other 
                    half of the row
                -->
                <div class="col-md-7">
                    <h2>Latest Sightings</h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="25%">Sighting ID</th>
                            <th width="50%">Sighting Location</th>
                            <th width="25%">Sighting Date</th>
                            
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
                               
                            </tr>
                        </c:forEach>
                    </table>           
                    
                </div> <!-- End col div -->

            </div> <!-- End row div -->    
            
            
        </div> <!-- End container -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>


