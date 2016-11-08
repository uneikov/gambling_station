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
    <jsp:include page="fragments/headTag.jsp"/>
    <jsp:include page="fragments/stakeHeader.jsp"/>
    <link rel="stylesheet" href="webjars/datatables/1.10.12/css/dataTables.bootstrap.min.css">
</head>
<body>

<%--<c:set var="selected" value="${stakes.get(0).horse.name}"/>--%>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="stakes.title"/></h3>
            <div class="view-box">
                <!-- Date & Time & Wins Filtration Form -->
                <form method="post" class="form-horizontal" role="form" id="filter">

                        <div class="form-inline">
                            <div class="input-group">
                                <span class="input-group-addon" style="width: inherit"></span>
                                <label for="startDate" class="control-label col-sm-2"><fmt:message key="stakes.startDate"/></label>
                                <input class="form-control" type="date" id="startDate" name="startDate"
                                       value="${param.startDate}">
                            </div>
                            <div class="input-group ">
                                <span class="input-group-addon" style="width: inherit"></span>
                                <label for="endDate" class="control-label col-sm-2"><fmt:message key="stakes.endtDate"/></label>
                                <input class="form-control" type="date" id="endDate" name="endtDate"
                                       value="${param.endDate}">
                            </div>
                        </div>
                        <div class="form-inline">
                            <div class="input-group col-sm-2">
                                <span class="input-group-addon" style="width: inherit"></span>
                                <label for="startTime" class="control-label col-sm-2"><fmt:message key="stakes.startTime"/>:</label>
                                <input class="form-control" type="time" id="startTime" name="startTime"
                                       value="${param.startTime}">
                            </div>
                            <div class="input-group col-sm-2">
                                <span class="input-group-addon" style="width: inherit"></span>
                                <label for="endTime" class="control-label col-sm-2"><fmt:message key="stakes.endTime"/>:</label>
                                <input class="form-control" type="time" id="endTime" name="endTime"
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

                </form>
                <!-- END Date & Time & Wins Filtration Form -->
                <hr>
                <table class="table table-striped display" id="stakestable">
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
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<!---------------------------------------- Modal window -------------------------------------------->
<div class="modal fade" id="editRow" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="modalTitle"></h2>
                <h6 class="test" id="test"></h6>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id" value="">
                    <%--<c:set var="selected" value="${selected}"/>--%>
                    <%--<input type="text" hidden="hidden" id="name" name="name" value="horseName">--%>
                    <%--<c:set var="selected" value="${name}"/>--%>
                    <!-- Select value of money for new stake -->
                    <div class="form-group">
                        <label for="value" class="control-label col-xs-3"><fmt:message key="stake.add"/>:</label>
                        <div id="value" class="col-xs-9">
                           <%-- <input class="form-control" id="stakeValue" name="stakeValue" type="number"
                                   step="0.01" min="0" max="${availableValue}" value="${availableValue}">--%>
                        </div>
                    </div>
                    <!-- Select horse for new stake -->
                    <div class="form-group">
                        <label for="horse" class="control-label col-xs-3"><fmt:message key="stake.horse"/>:</label>
                        <div id="horse" class="col-xs-9">
                            <%--<select class="form-control" id="horseName" name="horseName" >
                                <c:forEach var="horseName" items="${horsesNames}">
                                    <option value="${horseName}" ${horseName == selected ? 'selected="selected"' : ''}>${horseName}</option>
                                    &lt;%&ndash;<option value="${horseName}">${horseName}</option>&ndash;%&gt;
                                </c:forEach>
                            </select>--%>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal"><fmt:message key="common.cancel"/></button>
                <button class="btn btn-primary" type="button" onclick="save()"><fmt:message key="common.save"/></button>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var i18n = [];
    <c:forEach var='key' items='<%=new String[]{"common.update","common.delete","common.deleted","common.saved","common.enabled","common.disabled","common.failed"}%>'>
    i18n['${key}'] = '<fmt:message key="${key}"/>';
    </c:forEach>
    var edit_title ='<fmt:message key="stake.edit"/>';
    var add_title ='<fmt:message key="stake.add"/>';
</script>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript" src="resources/js/stakeDatatables.js"></script>
</html>
