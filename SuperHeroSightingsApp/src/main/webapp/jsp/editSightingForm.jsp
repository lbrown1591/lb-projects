<%-- 
    Document   : editSightingForm
    Created on : Nov 26, 2017, 10:16:29 PM
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
        <title>Sighting Edit</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Super Hero Sighting</h1>
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

            <h2 align=center >Edit Sighting</h2><br>
            <sf:form class="form-horizontal" role="form" modelAttribute="sighting"
                     action="editSighting" method="POST" id="sighting">
                <div class="form-group">
                    <input type="hidden" name="sightingId" value="${sighting.sightingId}">
                    <label for="edit-date" class="col-md-4 control-label">Sighting Date:</label>
                    <div class="col-md-8">
                        <sf:input type="date" class="form-control" id="edit-date"
                                  path="dateOfSighting" name="dateOfSighting" value="${date}" placeholder="Sighting Date" style="width: 300px;" required="true"/>
                        <sf:errors path="dateOfSighting" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit-location" class="col-md-4 control-label">Location:</label>
                        <div class="col-md-8">
                            <select name="location" form="sighting" style="width: 300px;" required="true">
                            <c:forEach var="currentLocation" items="${locationList}">
                                <option value=${currentLocation.locationId}
                                        ${currentLocation.locationId == sighting.location.locationId ? 'selected="true"' : '' }
                                        ><c:out value="${currentLocation.locationName}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="edit-heroes" class="col-md-4 control-label">Heroes Spotted:</label>
                    <select multiple="multiple" name="heroList[]" id="heroesSpotted" form="sighting" path="heroesSpotted" style="width: 300px;" required="true">
                        <c:forEach var="currentHero" items="${heroList}">
                            <option value=${currentHero.superPersonId} 
                                    <c:forEach var="sightingHero" items="${sightingHeroes}">
                                        ${sightingHero.superPersonId == currentHero.superPersonId ? 'selected' : ''}
                                    </c:forEach>
                                    ><c:out value="${currentHero.name}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Sighting"/>
                        <form  action="http://localhost:8080/SuperHeroSightingsApp/displaySightingPage">
                            <input type="submit" class="btn btn-default" value="Cancel Update">   
                        </form>
                    </div>

                </div>


            </sf:form> 

        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
