<%-- 
    Document   : organization
    Created on : Nov 19, 2017, 10:22:32 AM
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
        <title>Organizations</title>
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
                    <li role="presentation"
                        class="active">
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
                <c:if test="${error eq 'true'}">
                    <script type="text/javascript">
                        alert("Delete Error: That Organization is currently associated with a Super Hero. Please delete any relationships to this Organization before deleting.");
                    </script>
                </c:if>

                <div class="col-md-6">
                    <h2>Organizations</h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="30%">Organization Name</th>
                            <th width="40%">Organization Description </th>
                            <th width="15%"></th>
                            <th width="15%"></th>
                        </tr>
                        <c:forEach var="currentOrganization" items="${organizationList}">
                            <tr>
                                <td>
                                    <a href="displayOrganizationDetails?organizationId=${currentOrganization.organizationId}">
                                        <c:out value="${currentOrganization.organizationName}"/> 
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentOrganization.organizationDescription}"/>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="displayEditOrganizationForm?organizationId=${currentOrganization.organizationId}">
                                            Edit
                                        </a>
                                    </sec:authorize>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteOrganization?organizationId=${currentOrganization.organizationId}">
                                            Delete
                                        </a>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div>


                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <div class="col-md-6">
                    <h2>Add New Organization</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="createOrganization" id="organization">
                        <div class="form-group">
                            <label for="add-organization-name" class="col-md-4 control-label">Organization Name:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="organizationName" placeholder="Organization Name" maxlength="50" required="true" style="width: 300px;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-organization-description" class="col-md-4 control-label">Organization Description:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="organizationDescription" placeholder="Organization Description" maxlength="100" required="true" style="width: 300px;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-organization-phone" class="col-md-4 control-label">Phone Number:<br> (format: xxx-xxx-xxxx)</label>
                            <div class="col-md-8">
                                <input type="tel(format: xxxx-xxx-xxxx)" class="form-control" name="phone" pattern="^\d{3}-\d{3}-\d{4}$" placeholder="Organization Phone Number" required="true" style="width: 300px;"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="add-organization-email" class="col-md-4 control-label">Organization Email:</label>
                            <div class="col-md-8">
                                <input type="email" class="form-control" name="email" placeholder="Organization Email" required="true" style="width: 300px;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-location" class="col-md-4 control-label">Location:</label>
                            <div class="col-md-8">
                                <select name="location" form="organization" required="true" style="width: 300px;">
                                    <option disabled="disabled" value="" selected>Select Location</option>
                                    <c:forEach var="currentLocation" items="${locationList}">
                                        <option value=${currentLocation.locationId}><c:out value="${currentLocation.locationName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>


                        </div>

                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Create Organization"/>
                            </div>
                        </div>
                    </form>

                </div> 
                </sec:authorize>
            </div>
        </div>
</html>


