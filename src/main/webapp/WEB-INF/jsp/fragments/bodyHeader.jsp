<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="stakes" class="navbar-brand"><spring:message code="app.title"/></a>
        <a class="navbar-brand" id="station" style="color: white"></a>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form:form class="navbar-form" action="logout" method="post">
                        <sec:authorize access="isAuthenticated()">
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <a class="btn btn-danger" role="button" href="horses"><spring:message code="horses.title"/></a>
                                <a class="btn btn-success" role="button" href="races"><spring:message code="races.title"/></a>
                                <a class="btn btn-info" role="button" href="users"><spring:message code="users.title"/></a>
                            </sec:authorize>
                            <a class="btn btn-info" role="button" href="stakes"><spring:message code="stakes.title"/></a>
                            <button type="button" id="addButton" class="btn btn-primary" data-toggle="modal" onclick=checkAddStatus()><spring:message code="stake.add"/></button>
                            <a class="btn btn-info" role="button" href="profile">${userTo.name} <spring:message code="app.profile"/></a>
                            <input type="submit" class="btn btn-primary" value="<spring:message code="app.logout"/>">
                        </sec:authorize>
                    </form:form>
                </li>
                <jsp:include page="lang.jsp"/>
            </ul>
        </div>
    </div>
</div>
