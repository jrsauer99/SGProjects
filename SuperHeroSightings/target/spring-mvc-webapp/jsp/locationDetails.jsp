<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Location Details</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Locations Details</h1>
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
                        Name: <c:out value="${location.locName}"/> 
                    </p>
                    <p>
                        Description: <c:out value="${location.description}"/>
                    </p>
                    <p>
                        Street: <c:out value="${location.street}"/>
                    </p>
                    <p>
                        City: <c:out value="${location.city}"/>
                    </p>
                    <p>
                        State: <c:out value="${location.state}"/>
                    </p>
                    <p>
                        Zip Code: <c:out value="${location.zipCode}"/>
                    </p>
                    <p>
                        Country: <c:out value="${location.country}"/>
                    </p>

                    <p>
                        Latitude: <c:out value="${coordinate.latitude}"/>
                    </p>
                    <p>
                        Longitude: <c:out value="${coordinate.longitude}"/>
                    </p>
                </div>  

            </div>    
        </div>




        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>