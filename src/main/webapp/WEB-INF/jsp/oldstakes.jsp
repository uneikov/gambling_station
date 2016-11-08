<!DOCTYPE html>
<%@ page import="com.uran.gamblingstation.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="fn" uri="http://gamblingstation.uran.com/functions" %>--%>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Stake list</title>
    <link rel="stylesheet" href="webjars/datatables/1.10.12/css/dataTables.bootstrap.min.css">
    <%--<link rel="stylesheet" href="webjars/datatables/1.10.12/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">--%>
    <link rel="shortcut icon" href="resources/images/icon-hr.jpg">
    <link rel="stylesheet" href="resources/css/style.css">
    <jsp:include page="fragments/stakeHeader.jsp"/>
    <%-- <link rel="stylesheet" type="text/css"
           href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">--%>
</head>
<body>

<!--  Get user id for processing in modal -->
<c:set var="userId" value="${stakes.get(0).user.id}"/>
<c:set var="userMaxStakeValue" value="${stakes.get(0).user.wallet.cash}"/>
<!--  Set variable to store horses list -->
<%--<c:set var="horsesList" value="${stakes.get(0).horse.name}" scope="request"/>--%>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="stakes.title"/></h3>
            <div class="view-box">
                <!-- Date & Time & Wins Filtration Form -->
                <form method="post" class="form-horizontal" role="form" id="filter">
                    <div class="container">
                        <div class="form-inline">
                            <div class="input-group col-sm-5">
                                <span class="input-group-addon" style="width: inherit"></span>
                                <label for="fromDate" class="control-label col-sm-2"><fmt:message key="stakes.startDate"/></label>
                                <input class="form-control" type="date" id="fromDate" name="startDate"
                                       value="${param.startDate}">
                            </div>
                            <div class="input-group col-sm-5">
                                <span class="input-group-addon" style="width: inherit"></span>
                                <label for="toDate" class="control-label col-sm-2"><fmt:message key="stakes.endtDate"/></label>
                                <input class="form-control" type="date" id="toDate" name="startDate"
                                       value="${param.endDate}">
                            </div>
                        </div>
                        <div class="form-inline">
                            <div class="input-group col-sm-5">
                                <span class="input-group-addon" style="width: inherit"></span>
                                <label for="fromTime" class="control-label col-sm-2"><fmt:message key="stakes.startTime"/>:</label>
                                <input class="form-control" type="time" id="fromTime" name="startTime"
                                       value="${param.startTime}">
                            </div>
                            <div class="input-group col-sm-5">
                                <span class="input-group-addon" style="width: inherit"></span>
                                <label for="toTime" class="control-label col-sm-2"><fmt:message key="stakes.endTime"/>:</label>
                                <input class="form-control" type="time" id="toTime" name="endTime"
                                       value="${param.endTime}">
                            </div>
                        </div>

                        <form class="form-inline">
                            <div class="input-group">
                                <span class="input-group-addon" style="width: inherit"></span>
                                <label for="options" class="control-label col-sm-5"><fmt:message
                                        key="filter.options_select"/></label>
                                <select id="options" name="option" class="form-control">
                                    <option value="all"><fmt:message key="filter.all"/></option>
                                    <option value="winned"><fmt:message key="filter.winned"/></option>
                                    <option value="loosed"><fmt:message key="filter.loosed"/></option>
                                </select>
                            </div>
                            <div class="input-group col-sm-5">
                                <button type="submit" class="btn btn-primary"><fmt:message
                                        key="filter.select"/></button>
                            </div>
                        </form>
                    </div>
                </form>
                <!-- END Date & Time & Wins Filtration Form -->
                <hr>
                <table class="table table-striped display" id="stakeTable">
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
                        <%--    <c:set var="user" value="${stake.user.id}"/>--%>
                        <jsp:useBean id="stake" scope="page" type="com.uran.gamblingstation.model.Stake"/>
                        <tr class="${stake.wins ? 'winned' : 'loosed'}">
                            <c:set var="id" value="${stake.id}"/>
                            <td>
                                    <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%=TimeUtil.toString(stake.getDateTime())%>
                                    <%-- ${fn:formatDateTime(stake.dateTime)}--%>
                            </td>
                            <td>${stake.stakeValue}</td>
                            <td>${stake.horse.name}</td>
                            <td>${stake.horse.wins}</td>
                            <td>${stake.amount}</td>
                                <%-- <td><a href="stakes/update/?id=${stake.id}"><fmt:message key="stake.update"/></a></td>--%>
                            <td><a class="btn btn-xs btn-primary edit" href="stakes/update/?id=${id}" id="${id}"><fmt:message key="common.update"/></a></td>
                                <%--<td><a href="stakes/delete/?id=${stake.id}"><fmt:message key="stake.delete"/></a></td>--%>
                            <td><a class="btn btn-xs btn-danger delete" href="stakes/delete/?id=${id}" id="${id}"><fmt:message key="common.delete"/></a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<!---------------------------------------- Modal window -------------------------------------------->
<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="stake.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id" value="">
                    <input type="number" hidden="hidden" id="userId" name="userId" value="${userId}">
                    <!-- Select value of money for new stake -->
                    <div class="form-group">
                        <label for="stakeValue" class="control-label col-xs-3"><fmt:message key="stake.add"/>:</label>

                        <div class="col-xs-9">
                            <input class="form-control" id="stakeValue" name="stakeValue" type="number"
                                   step="0.01" min="0" max="${userMaxStakeValue}" value="${userMaxStakeValue}">
                        </div>
                    </div>
                    <!-- Select horse for new stake -->
                    <div class="form-group">
                        <label for="horseName" class="control-label col-xs-3"><fmt:message key="stake.horse"/>:</label>
                        <div class="col-xs-9">
                            <select class="form-control" id="horseName" name="horseName">
                                <c:forEach var="horseName" items="${horsesNames}">
                                    <%-- <option value="${horse_name}" ${horse_name == selected ? 'selected="selected"' : ''}>${horse_name}</option>--%>
                                    <option value="${horseName}">${horseName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <!-- Submit new stake -->
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript" src="resources/js/stakeDatatables.js"></script>
</html>
