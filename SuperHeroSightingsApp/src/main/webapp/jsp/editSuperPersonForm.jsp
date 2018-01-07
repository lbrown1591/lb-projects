<%-- 
    Document   : editSuperPersonForm
    Created on : Nov 25, 2017, 10:39:57 PM
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
        <title>Super Hero Edit</title>
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
                            Location
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
            <h2 align=center >Edit Super Hero</h2><br>
            <sf:form class="form-horizontal" role="form" modelAttribute="hero"
                     action="editSuperPerson" method="POST" id="hero">
                <div class="form-group">
                    <input type="hidden" name="superPersonId" value="${hero.superPersonId}">
                    <label for="edit-hero-name" class="col-md-4 control-label">Super Hero Name:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="edit-hero-name"
                                  path="name" placeholder="Super Hero Name" maxlength="50" style="width: 300px;" required="true"/>
                        <sf:errors path="name" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="edit-hero-description" class="col-md-4 control-label">Super Hero Description:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="edit-hero-description"
                                  path="description" placeholder="Super Hero Description" maxlength="100" style="width: 300px;" required="true"/>
                        <sf:errors path="description" cssclass="error"></sf:errors>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-powers" class="col-md-4 control-label">Super Powers:</label>
                        <select multiple="multiple" name="powerList[]" id="powers" form="hero" path="powers" style="width: 300px;" required="true">
                        <c:forEach var="currentPower" items="${powerList}">
                            <option value=${currentPower.powerId} 
                                    <c:forEach var="heroPower" items="${heroPowers}">
                                        ${heroPower.powerId == currentPower.powerId ? 'selected' : ''}
                                    </c:forEach>
                                    ><c:out value="${currentPower.powerName}"/></option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="edit-organizations" class="col-md-4 control-label">Organizations:</label>
                    <select multiple="multiple" name="organizationList[]" id="organizations" form="hero" path="organizations" style="width: 300px;" required="true">
                        <c:forEach var="currentOrganization" items="${organizationList}">
                            <option value=${currentOrganization.organizationId}
                                    <c:forEach var="heroOrganization" items="${heroOrganizations}">
                                        ${heroOrganization.organizationId == currentOrganization.organizationId ? 'selected' : ''}
                                    </c:forEach>
                                    ><c:out value="${currentOrganization.organizationName}"/></option>
                        </c:forEach>
                    </select>
                </div>


                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Super Hero"/>
                        <form  action="http://localhost:8080/SuperHeroSightingsApp/displaySuperPersonPage">
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
