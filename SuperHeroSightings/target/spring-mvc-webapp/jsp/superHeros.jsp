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
            <h1>Super Heros</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sightings">Sightings</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/superHeros">Super Heros</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/locations">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organizations">Organizations</a></li>
                </ul>    
            </div>

            <!-- ***************************************** -->
            <div class="row">
                <!-- 
                    Add a col to hold the summary table - have it take up half the row 
                -->
                <div class="col-md-6">
                    <h2>Super Heros</h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="30%">Super Hero Name</th>
                            <th width="60%">Description</th>
                            <th width="5%"></th>
                            <th width="5%"></th>
                        </tr>
                        <c:forEach var="currentSuperHero" items="${superHeroList}">
                            <tr>
                                <td>
                                    <a href="displaySuperHeroDetails?superHeroID=${currentSuperHero.superHeroID}">
                                        <c:out value="${currentSuperHero.superHeroName}"/> 
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentSuperHero.description}"/>
                                </td>
                                <td>
                                    <a href="editSuperHeroForm?superHeroID=${currentSuperHero.superHeroID}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                </td>
                                <td>
                                    <a type="button" class="close" aria-label="Close" href="deleteSuperHero?superHeroID=${currentSuperHero.superHeroID}">
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
                    <h2 class="col-md-offset-1">Add Super Power</h2>
                    <sf:form class="form-horizontal" 
                             role="form" method="POST" 
                             action="createSuperPower"
                             modelAttribute="superPower">
                        <div class="form-group">
                            <label for="add-superPower" class="col-md-4 control-label">Super Power:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="superPower" placeholder="Super Power"/>
                                <sf:errors path="superPower" cssClass="error"></sf:errors>
                                <sf:hidden path="superPowerID"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Add Super Power"/>
                            </div>
                        </div>
                    </sf:form>    

                    <h2 class="col-md-offset-1">Add Super Hero</h2>
                    <sf:form class="form-horizontal" 
                             role="form" method="POST" 
                             action="createSuperHero"
                             modelAttribute="superHero">
                        <div class="form-group">
                            <label for="add-superHeroName" class="col-md-4 control-label">Super Hero Name:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="superHeroName" placeholder="Super Hero Name"/>
                                <sf:errors path="superHeroName" cssClass="error"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-superHeroDesc" class="col-md-4 control-label">Description:</label>
                                <div class="col-md-8">
                                <sf:input type="text" class="form-control" path="description" placeholder="Description"/>
                                <sf:errors path="description" cssClass="error"></sf:errors>
                                <sf:hidden path="superHeroID"/>
                            </div>
                        </div>>
                        <!-- SELECT SUPER POWERS -->
                        <div class="form-group">
                            <label for="add-superHeroPower" class="col-md-4 control-label">Super Powers:</label>
                            <div class="col-md-8">
                                <select multiple name="selectedSuperPowers[]" class="form-control">
                                    <c:forEach items="${superPowerList}" var="currentPower" >
                                        <option value="${currentPower.superPowerID}" label="${currentPower.superPower}"/>
                                    </c:forEach>
                                </select>     
                            </div>
                        </div>
                        <!-- END SELECT SUPER POWERS -->
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Add Super Hero"/>
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

