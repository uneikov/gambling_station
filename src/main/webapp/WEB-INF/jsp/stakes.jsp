<%@ page import="com.uran.gamblingstation.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="fn" uri="http://gamblingstation.uran.com/functions" %>--%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Stake list</title>
    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fun:substring(url, 0, fun:length(url) - fun:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<section>
    <h2><a href="${pageContext.request.contextPath}/"><fmt:message key="app.home"/></a></h2>
    <h3><fmt:message key="stakes.title"/></h3>
    <form method="post" action="stakes/filter">
        <dl>
            <dt>From Date:</dt>
            <dd><input type="date" name="startDate" value="${startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date:</dt>
            <dd><input type="date" name="endDate" value="${endDate}"></dd>
        </dl>
        <dl>
            <dt>From Time:</dt>
            <dd><input type="time" name="startTime" value="${startTime}"></dd>
        </dl>
        <dl>
            <dt>To Time:</dt>
            <dd><input type="time" name="endTime" value="${endTime}"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="filter.options_select"/></dt>
            <dd>
                <select name="option" id="filter_options">
                    <option value="all"><fmt:message key="filter.all"/></option>
                    <option value="winned"><fmt:message key="filter.winned"/></option>
                    <option value="loosed"><fmt:message key="filter.loosed"/></option>
                </select>
            </dd>
        </dl>
        <button type="submit"><fmt:message key="filter.select"/></button>
    </form>
    <hr>
    <a href="stakes/create"><fmt:message key="stake.create"/></a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><fmt:message key="stake.date"/></th>
            <th><fmt:message key="stake.value"/></th>
            <th><fmt:message key="stake.horse"/></th>
            <th><fmt:message key="stake.wins"/></th>
            <th><fmt:message key="stake.amount"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${stakes}" var="stake">
            <jsp:useBean id="stake" scope="page" type="com.uran.gamblingstation.model.Stake"/>
            <tr class="${stake.wins ? 'winned' : 'loosed'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%=TimeUtil.toString(stake.getDateTime())%>
                       <%-- ${fn:formatDateTime(stake.dateTime)}--%>
                </td>
                <td>${stake.stakeValue}</td>
                <td>${stake.horse.name}</td>
                <td>${stake.wins}</td>
                <td>${stake.amount}</td>
                <td><a href="stakes/update/?id=${stake.id}"><fmt:message key="stake.update"/></a></td>
                <td><a href="stakes/delete/?id=${stake.id}"><fmt:message key="stake.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
