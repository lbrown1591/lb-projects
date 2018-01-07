<%-- 
    Document   : editUserForm
    Created on : Dec 19, 2017, 12:27:13 PM
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
        <title>Super Hero Sightings</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Edit User</h1>
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



            <sf:form class="form-horizontal" role="form" modelAttribute="user"
                     action="editUser" method="POST" id="user">
                <div class="form-group">
                    <input type="hidden" name="originalUsername" value="${user.username}">
                    <label for="edit-username" class="col-md-4 control-label">Username:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="edit-username"
                                  path="username" placeholder="Username" maxlength="50" style="width: 300px;" required="true"/>
                        <sf:errors path="username" cssclass="error"></sf:errors>
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="edit-authorities" class="col-md-4 control-label">Administrator Authorities: </label>
                        <input type="checkbox" name="adminRole" value="ROLE_ADMIN"
                        ${isAdmin == true ? 'checked' : ''}
                        >Administrator<br>
                </div>

                <div class="form-group">
                    <label for="edit-password" class="col-md-4 control-label">Would you like to update this user's password? </label>
                    <input type="checkbox" name="change" value="change">Yes update password<br>
                    <input type="checkbox" name="change" value="">No<br>
                </div>

                <div class="form-group">
                    <label for="update-password" class="col-md-4 control-label">Updated Password: <br> (If needed)</label>
                     <div class="col-md-8">
                        <input type="text" class="form-control" id="update-password"
                                  name="password" placeholder="New Password" style="width: 300px;" maxlength="40"/>
                          
                        </div>
                    </div>
                        
                        
                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <input type="submit" class="btn btn-default" value="Update User"/>
                        </div>
                    </div>
            </sf:form>                
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
