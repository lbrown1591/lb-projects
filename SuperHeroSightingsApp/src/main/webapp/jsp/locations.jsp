<%-- 
    Document   : locations
    Created on : Nov 17, 2017, 8:29:39 PM
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
        <title>Locations</title>
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
                        class="active">
                        <a href="${pageContext.request.contextPath}/">
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

            <div class="row">

                <div class="col-md-6">
                    <h2>Locations</h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="40%">Location Name</th>
                            <th width="30%">Location Description </th>
                            <th width="15%"></th>
                            <th width="15%"></th>
                        </tr>
                        <c:forEach var="currentLocation" items="${locationList}">
                            <tr>
                                <td>
                                    <a href="displayLocationDetails?locationId=${currentLocation.locationId}">
                                        <c:out value="${currentLocation.locationName}"/> 
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentLocation.description}"/>
                                </td>
                                <td>
                                    <a href="displayEditLocationForm?locationId=${currentLocation.locationId}">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteLocation?locationId=${currentLocation.locationId}">
                                            Delete
                                        </a>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div> 

                <c:if test="${error eq 'true'}">
                    <script type="text/javascript">
                        alert("Delete Error: That Location is currently associated with an Organization or Sighting. Please delete any relationships to this Location before deleting.");
                    </script>
                </c:if>

                <div class="col-md-6">
                    <h2>Add New Location</h2>

                    <sf:form class="form-horizontal" role="form" 
                             action="createLocation" modelAttribute="location" method="POST">
                        <div class="form-group">
                            <label for="add-location-name" class="col-md-4 control-label">Location Name:</label>
                            <div class="col-md-8">
                                <sf:input type="text" class="form-control" id="add-location-name"
                                          path="locationName" placeholder="Location Name" style="width: 300px;" maxlength="40"/>
                                <sf:errors path="locationName" cssclass="error"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-location-description" class="col-md-4 control-label">Location Description:</label>
                                <div class="col-md-8">
                                <sf:input type="text" class="form-control" id="add-location-description"
                                          path="description" placeholder="Location Description" style="width: 300px;" maxlength="100"/>
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
                                <input type="submit" class="btn btn-default" id="addLocation" value="Add Location"/>
                            </div>
                        </div>
                    </sf:form> 






                </div> 

            </div> 

        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
