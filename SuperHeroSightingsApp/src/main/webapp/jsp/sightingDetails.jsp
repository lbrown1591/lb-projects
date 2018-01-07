<%-- 
    Document   : sightingDetails
    Created on : Nov 27, 2017, 9:59:27 AM
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
        <title>Sighting Details</title>

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
            <h2>Sighting Details</h2>
            <p>
                Date of Sighting: <c:out value="${date}"/> 
            </p>
            <p>
                Location of Sighting: <c:out value="${sighting.location.locationName}"/>
            </p>
            <p>
                Super Heroes Spotted: <ul>
                <c:forEach var="currentHero" items="${heroList}">
                    <li>    
                        <c:out value="${currentHero.name}"/>
                    </li>
                </c:forEach>
            </ul>
        </p>

</html>
