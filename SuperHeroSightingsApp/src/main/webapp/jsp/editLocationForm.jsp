<%-- 
    Document   : editLocationForm
    Created on : Nov 17, 2017, 10:48:15 PM
    Author     : softwareguild
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Location Edit</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Super Hero Sightings</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/">
                            Home
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/displayLocationPage">
                            Locations
                        </a>
                    </li>
                    <li role="presentation"
                        >
                        <a href="${pageContext.request.contextPath}/displayOrganizationPage">
                            Organizations
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/displaySuperPersonPage">
                            Super Heros
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/displaySightingPage">
                            Sightings
                        </a>
                    </li>
                     <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"
                            >
                            <a href="${pageContext.request.contextPath}/displayUserList">
                                User Administration
                            </a>
                        </li>
                    </sec:authorize>
                </ul>    
            </div>

            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name}
                    | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                </p>
            </c:if>

            <h2 align=center >Edit Location</h2><br>
            <sf:form class="form-horizontal" role="form" modelAttribute="location"
                     action="editLocation" method="POST">
                <div class="form-group">
                    <label for="add-location-name" class="col-md-4 control-label">Location Name:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-location-name"
                                  path="locationName" placeholder="Location Name" maxlength="40" style="width: 300px;"/>
                        <sf:errors path="locationName" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-location-description" class="col-md-4 control-label">Location Description:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-location-description"
                                  path="description" placeholder="locationDescription" maxlength="100" style="width: 300px;"/>
                        <sf:errors path="description" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-latitude" class="col-md-4 control-label">Latitude:</label>                          
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-latitude"
                                  path="latitude" placeholder="Latitude" style="width: 300px;"/>
                        <sf:errors path="latitude" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-longitude" class="col-md-4 control-label">Longitude:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-longitude"
                                  path="longitude" placeholder="Longitude" style="width: 300px;"/>
                        <sf:errors path="longitude" cssclass="error"></sf:errors>
                        <sf:hidden path="locationId"/>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Location"/>
                        <form  action="http://localhost:8080/SuperHeroSightingsApp/displayLocationPage">
                            <input type="submit" class="btn btn-default" value="Cancel Update">   
                        </form>
                    </div>
                </div>
            </sf:form> 

        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>