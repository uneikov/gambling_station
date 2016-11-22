<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">

        <a href="stakes" class="navbar-brand"><fmt:message key="app.title"/></a>

        <div class="collapse navbar-collapse">
            <form class="navbar-form navbar-right">
                <a class="btn btn-danger" role="button" href="horses"><fmt:message key="horses.title"/></a>
                <a class="btn btn-success" role="button" href="races"><fmt:message key="races.title"/></a>
                <a class="btn btn-info" role="button" href="users"><fmt:message key="users.title"/></a>
                <a class="btn btn-primary" role="button" href=""><fmt:message key="app.login"/></a>
            </form>
        </div>

    </div>
</div>