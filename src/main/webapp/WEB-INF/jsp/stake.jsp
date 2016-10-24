<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><fmt:message key="${stake.isNew() ? 'stake.add' : 'stake.edit'}"/></h3>
    <hr>
    <jsp:useBean id="stake" type="com.uran.gamblingstation.model.Stake" scope="request"/>
    <form method="post" action="stakes">
        <input type="hidden" name="id" value="${stake.id}">
        <input type="hidden" name="user_id" value="${stake.user.id}">
        <input type="hidden" name="dateTime" value="${stake.dateTime}">
        <c:set var="selected" value="${stake.horse.name}"/>
        <dl>
            <dt><fmt:message key="stake.date"/>:</dt>
            <dd><input type="datetime-local" value="${stake.dateTime}" name="dateTime" disabled></dd>
        </dl>
        <dl>
            <dt><fmt:message key="stake.value"/>:</dt>
            <dd><input type="number" value="${stake.stakeValue}" size=40 name="stake_value"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="stake.horse"/>:</dt>
            <dd>
                <select name="horse_name">
                    <c:forEach var="horse" items="${horses}">
                        <option value="${horse}" ${horse == selected ? 'selected="selected"' : ''}>${horse}</option>
                    </c:forEach>
                </select>
            </dd>
        </dl>
        <button type="submit"><fmt:message key="common.save"/></button>
        <button onclick="window.history.back()"><fmt:message key="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
