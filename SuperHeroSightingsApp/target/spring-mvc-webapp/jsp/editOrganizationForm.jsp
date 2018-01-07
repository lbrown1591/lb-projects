<%-- 
    Document   : editOrganizationForm
    Created on : Nov 20, 2017, 6:28:53 PM
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
        <title>Organization Edit</title>
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
            <h2 align=center >Edit Organization</h2><br>
            <sf:form class="form-horizontal" role="form" modelAttribute="organization"
                     action="editOrganization" method="POST" id="organization">
                <div class="form-group">
                    <label for="edit-organization-name" class="col-md-4 control-label">Organization Name:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="edit-organization-name"
                                  path="organizationName" placeholder="Organization Name" maxlength="50" style="width: 300px;" required="true"/>
                        <sf:errors path="organizationName" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit-organization-description" class="col-md-4 control-label">Organization Description:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="edit-organization-description"
                                  path="organizationDescription" placeholder="Organization Description" style="width: 300px;" maxlength="100" required="true"/>
                        <sf:errors path="organizationDescription" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit-phone" class="col-md-4 control-label">Phone Number:<br> (format: xxx-xxx-xxxx)</label>                          
                        <div class="col-md-8">
                        <sf:input type="tel(format: xxxx-xxx-xxxx)" class="form-control" id="edit-phone" pattern="^\d{3}-\d{3}-\d{4}$"
                                  path="phone" placeholder="Phone Number" style="width: 300px;" required="true"/>
                        <sf:errors path="phone" cssclass="error"></sf:errors>
                        </div>
                        <input type="hidden" name="organizationId" value="${organization.organizationId}">
                </div>
                <div class="form-group">
                    <label for="edit-email" class="col-md-4 control-label">Email:</label>
                    <div class="col-md-8">
                        <sf:input type="email" class="form-control" id="edit-email"
                                  path="email" style="width: 300px;"/>
                        <sf:errors path="email" cssclass="error"></sf:errors>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-location" class="col-md-4 control-label">Location:</label>
                        <div class="col-md-8">
                            <select name="locationList" form="organization" style="width: 300px;">
                            <c:forEach var="currentLocation" items="${locationList}">
                                <option value=${currentLocation.locationId}
                                        ${currentLocation.locationId == organization.location.locationId ? 'selected="true"' : '' }
                                        ><c:out value="${currentLocation.locationName}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Organization"/>
                        <form  action="http://localhost:8080/SuperHeroSightingsApp/displayOrganizationPage">
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
