<%-- 
    Document   : displayUserList
    Created on : Dec 18, 2017, 10:12:01 PM
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
        <title>User List</title>

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">     
        <link href="${pageContext.request.contextPath}/css/myStyle.css" rel="stylesheet">     

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
                        >
                        <a href="${pageContext.request.contextPath}/displaySightingPage">
                            Sightings
                        </a>
                    </li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation"
                            class="active">
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
                        alert("Duplicate Username Error: That username is already in use. Please try another username.");
                    </script>
                </c:if>

                <div class="col-md-6">


                    <h2>Users</h2>
                    <table id="usersTable" class="table table-hover">
                        <tr>
                            <th width="50%">Username</th>
                            <th width="25%"></th>
                            <th width="25%"></th>
                        </tr>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>
                                    <c:out value="${user.username}"/>
                                </td>
                                <td>
                                    <a href="displayEditUserForm?username=${user.username}">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteUser?username=${user.username}">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div>

                <div class="col-md-6">
                    <h2>Add New User</h2>
                    <form class="form-horizontal" 
                          role="form" method="POST" 
                          action="addUser" id="user">
                        <div class="form-group">
                            <label for="add-username" class="col-md-4 control-label">Username:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="username" placeholder="Username" maxlength="50" required="true" style="width: 250px;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-password" class="col-md-4 control-label">Password:</label>
                            <div class="col-md-8">
                                <input type="password" class="form-control" name="password" placeholder="Password" maxlength="100" required="true" style="width: 250px;"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="check-admin" class="col-md-4 control-label">Administrative User?:</label>
                            <div class="col-md-8">
                                <input type="checkbox"  name="isAdmin" value="yes"/>

                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" value="Add User"/>
                            </div>
                        </div>
                    </form>

                </div> 


            </div>
        </div>
</html>

