<%--
  Created by IntelliJ IDEA.
  User: URAN
  Date: 29.10.2016
  Time: 13:15
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">

        <a href="stakes" class="navbar-brand"><fmt:message key="app.title"/></a>
        <h5 class="logo" id="station" style="color: white">LALALALA</h5>
        <div class="collapse navbar-collapse">
            <form class="navbar-form navbar-right">
                <a class="btn btn-info" role="button" href="stakes"><fmt:message key="stakes.title"/></a>
               <%-- <a class="btn btn-info" role="button" onclick=wallet()><fmt:message key="wallet.title"/></a>--%>
               <%-- <a class="btn btn-primary" role="button" href="stakes/create"><fmt:message key="stake.add"/></a>--%>
                <%--<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editRow"><fmt:message key="stake.add"/></button>--%>
                <%--<button type="button" class="btn btn-primary" data-toggle="modal" onclick=getHorses()><fmt:message key="stake.add"/></button>--%>
                <button type="button" id="addButton" class="btn btn-primary" data-toggle="modal"  onclick=checkAddStatus()><fmt:message key="stake.add"/></button>
                <a class="btn btn-info" role="button" href="${pageContext.request.contextPath}/"><fmt:message key="app.login"/></a>
            </form>
        </div>

    </div>
</div>