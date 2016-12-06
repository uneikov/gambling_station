<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="fragments/info.jsp"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<%--<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header navbar-brand"><spring:message code="app.title"/></div>
        <div class="navbar-collapse collapse">
            <form:form class="navbar-form navbar-right" role="form" action="spring_security_check" method="post">
                <div class="form-group">
                    <input type="text" placeholder="Email" class="form-control" name='username'>
                </div>
                <div class="form-group">
                    <input type="password" placeholder="Password" class="form-control" name='password'>
                </div>
                &lt;%&ndash;<button type="submit" class="btn btn-success"><spring:message code="app.login"/></button>&ndash;%&gt;
            </form:form>
        </div>
    </div>
</div>--%>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header navbar-brand"><spring:message code="app.title"/></div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form:form class="navbar-form" role="form" action="spring_security_check"
                               method="post">
                        <div class="form-group">
                            <input type="text" placeholder="Email" class="form-control" name='username'>
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="Password" class="form-control" name='password'>
                        </div>
                        <button type="submit" class="btn btn-success"><spring:message code="app.login"/></button>
                    </form:form>
                </li>
                <jsp:include page="fragments/lang.jsp"/>
            </ul>
        </div>
    </div>
</div>
<div class="jumbotron">
    <div class="container">
        <c:if test="${error}">
            <div class="error">
                    ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="message">
                <spring:message code="${message}"/>
            </div>
        </c:if>
        <p>
        <p><h5><b>Test credentials:</b></h5></p>
        <p><h5>User1 login: <b>user1@yandex.ru / password1</b></h5></p>
        <p><h5>User2 login: <b>user2@yandex.ru / password2</b></h5></p>
        <p><h5>Admin login: <b>admin@gmail.com / admin</b></h5></p>
        <br>

        <p><a class="btn btn-primary btn-lg" role="button" href="register"><spring:message code="app.register"/> &raquo;</a></p>
        <p><a class="btn btn-info btn-lg" role="button" href="about"><spring:message code="app.about"/></a></p>

    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
<div class="navbar navbar-inverse navbar-fixed-bottom" role="banner" data-spy="affix">
    <div class="scroll">
        <h3>Horses, races, stakes and more...&nbsp;&nbsp;&nbsp;Start gambling now with us!&nbsp;&nbsp;&nbsp;Are you ready to win?&nbsp;&nbsp;&nbsp;Yes you will!</h3>
    </div>
</div>
</body>
</html>