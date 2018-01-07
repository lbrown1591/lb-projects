<%-- 
    Document   : superPersons
    Created on : Nov 21, 2017, 8:45:04 PM
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
        <title>Super Heros</title>

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
                   
                    <li role="presentation"
                        class="active">
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
                    <h2>Super Heros</h2>
                    <table id="superPersonTable" class="table table-hover">
                        <tr>
                            <th width="30%">Super Hero Name</th>
                            <th width="40%">Super Hero Description </th>
                            <th width="15%"></th>
                            <th width="15%"></th>
                        </tr>
                        <c:forEach var="currentSuperPerson" items="${heroList}">
                            <tr>
                                <td>
                                    <a href="displaySuperPersonDetails?superPersonId=${currentSuperPerson.superPersonId}">
                                        <c:out value="${currentSuperPerson.name}"/> 
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentSuperPerson.description}"/>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_USER')">
                                        <a href="displayEditSuperPersonForm?superPersonId=${currentSuperPerson.superPersonId}">
                                            Edit
                                        </a>
                                    </sec:authorize>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteSuperPerson?superPersonId=${currentSuperPerson.superPersonId}">
                                            Delete
                                        </a>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div>


                <sec:authorize access="hasRole('ROLE_USER')">
                    <div class="col-md-6">
                        <h2>Add New Super Hero</h2>
                        <form class="form-horizontal" 
                              role="form" method="POST" 
                              action="createSuperPerson" id="superPerson">
                            <div class="form-group">
                                <label for="add-hero-name" class="col-md-4 control-label">Super Hero Name:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="superPersonName" maxlength="50" placeholder="Super Hero Name" required style="width: 300px;"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-hero-description" class="col-md-4 control-label">Super Hero Description:</label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="superPersonDescription" maxlength="100" placeholder="Super Person Description" required style="width: 300px;"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-powers" class="col-md-4 control-label">Super Powers:</label>
                                <select multiple="multiple" name="powerList[]" id="powers" form="superPerson" path="powers" required style="width: 300px;">
                                    <c:forEach var="currentPower" items="${powerList}">
                                        <option value=${currentPower.powerId}><c:out value="${currentPower.powerName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="add-organizations" class="col-md-4 control-label">Organizations:</label>
                                <select multiple="multiple" name="organizationList[]" id="organizations" form="superPerson" path="organizations" required style="width: 300px;">
                                    <c:forEach var="currentOrganization" items="${organizationList}">
                                        <option value=${currentOrganization.organizationId}><c:out value="${currentOrganization.organizationName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <input type="submit" class="btn btn-default" value="Create Super Hero"/>
                                </div>
                            </div>
                        </form>

                    </div> 
                </sec:authorize>
            </div>
        </div>
</html>
