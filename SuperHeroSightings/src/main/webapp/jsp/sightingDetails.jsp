<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sighting Details</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Sighting Details</h1>
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
            <!--  -->

            <div class="row">  
                <div class="col-md-5">

                    <img src="css/pics/tempMap.png">

                </div> 
                <!-- End col div --> 
                <div class="col-md-7">
                    <p>
                        <b>Location:</b> <c:out value="${sighting.location.locName}"/> 
                    </p>
                    <p>
                        <b>Date:</b> <c:out value="${sighting.sightingDate}"/>
                    </p>
                    <p>
                        <b>Super Heros: </b><br>
                        <c:forEach items="${sighting.superHeros}" var="currentHero">
                            <c:out value="${currentHero.superHeroName}"/> <br>
                        </c:forEach>
                    </p>

                </div>  

            </div>    
        </div>




        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>