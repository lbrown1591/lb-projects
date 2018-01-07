<%-- 
    Document   : sightings
    Created on : Nov 26, 2017, 8:22:40 PM
    Author     : softwareguild
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sightings</title>

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
                    <li role="presentation"
                        >
                        <a href="${pageContext.request.contextPath}/displayLocationPage">
                            Locations
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/displayOrganizationPage">
                            Organizations
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/displaySuperPersonPage">
                            Super Heros
                        </a>
                    </li>
                    <li role="presentation"
                        class="active">
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

            <div class="row">

                <div class="col-md-6">
                    <h2>Sightings</h2>
                    <table id="sightingsTable" class="table table-hover">
                        <tr>
                            <th width="30%">Sighting Location</th>
                            <th width="40%">Sighting Date </th>
                            <th width="15%"></th>
                            <th width="15%"></th>
                        </tr>
                        <c:forEach var="currentSighting" items="${sightingList}">
                            <tr>
                                <td>
                                    <a href="displaySightingDetails?sightingId=${currentSighting.sightingId}">
                                        <c:out value="${currentSighting.location.locationName}"/> 
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentSighting.dateOfSighting}"/>
                                </td>
                                <td>
                                    <a href="displayEditSightingForm?sightingId=${currentSighting.sightingId}">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteSighting?sightingId=${currentSighting.sightingId}">
                                            Delete
                                        </a>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div>


                <div class="col-md-6">
                    <h2>Add New Sighting</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="createSighting" id="sighting">
                        <div class="form-group">
                            <label for="add-date" class="col-md-4 control-label">Sighting Date:</label>
                            <div class="col-md-8">
                                <input type="date" class="form-control" name="sightingDate" placeholder="Sighting Date" required style="width: 300px;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-location" class="col-md-4 control-label">Sighting Location:</label>
                            <div class="col-md-8">
                                <select name="location" form="sighting" required style="width: 300px;">
                                    <option disabled="disabled" value="" selected>Select Location</option>
                                    <c:forEach var="currentLocation" items="${locationList}">
                                        <option value=${currentLocation.locationId}><c:out value="${currentLocation.locationName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="add-heros" class="col-md-4 control-label">Heroes Spotted:</label>
                            <select multiple="multiple" name="heroList[]" id="heroesSpotted" form="sighting" path="heroesSpotted" required style="width: 300px;">
                                <c:forEach var="currentHero" items="${heroList}">
                                    <option value=${currentHero.superPersonId}><c:out value="${currentHero.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Create Sighting"/>
                            </div>
                        </div>
                    </form>

                </div> 
            </div>
        </div>
</html>
