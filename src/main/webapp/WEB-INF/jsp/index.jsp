<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <p></p>
        <form method="post" action="users">
            <label for="login"><fmt:message key="app.login"/>:</label>
            <select id="login" name="userId">
            <option value="100000" selected>User1</option>
            <option value="100001">User2</option>
            <option value="100002">Admin</option>
            <option value="100003">Station</option>
        </select>
            <button type="submit"><fmt:message key="common.select"/></button>
        </form>
        <ul>
            <li><a href="users"><fmt:message key="users.title"/></a></li>
            <li><a href="stakes"><fmt:message key="stakes.title"/></a></li>
        </ul>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
