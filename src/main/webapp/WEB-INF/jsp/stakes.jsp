<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fun" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="fragments/headTag.jsp"/>
    <jsp:include page="fragments/bodyHeader.jsp"/>
    <link rel="stylesheet" href="webjars/datatables/1.10.12/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="webjars/datetimepicker/2.4.7/jquery.datetimepicker.css">
</head>
<body>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><spring:message code="stakes.title"/></h3>
            <div class="view-box">
                <!-- Date & Time & Wins Filtration Form -->
                <form:form method="post" class="form-horizontal" role="form" id="filter">

                    <div class="form-group">
                        <label for="startDate" class="control-label col-xs-2"><spring:message code="stakes.startDate"/></label>
                        <div class="col-xs-2">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                                <input class="form-control" type="date" id="startDate" name="startDate">
                            </div>
                        </div>
                        <label for="endDate" class="control-label col-xs-2"><spring:message code="stakes.endtDate"/></label>
                        <div class="col-xs-2">
                            <div class="input-group ">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                                <input class="form-control" type="date" id="endDate" name="endDate">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="startTime" class="control-label col-xs-2"><spring:message code="stakes.startTime"/>:</label>
                        <div class="col-xs-2">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                <input class="form-control" type="time" id="startTime" name="startTime">
                            </div>
                        </div>
                        <label for="endTime" class="control-label col-xs-2"><spring:message code="stakes.endTime"/>:</label>
                        <div class="col-xs-2">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                <input class="form-control" type="time" id="endTime" name="endTime">
                            </div>
                        </div>
                    </div>

                    <form:form class="form-group">
                        <label for="option" class="control-label col-xs-2"><spring:message code="filter.options_select"/></label>
                        <div class="col-xs-2">
                            <div class="input-group">
                                <span class="input-group-addon"><i
                                        class="glyphicon glyphicon-filter"></i></span>
                                <select id="option" name="option" class="form-control">
                                    <option value="all"><spring:message code="filter.all"/></option>
                                    <option value="winned"><spring:message code="filter.winned"/></option>
                                    <option value="loosed"><spring:message code="filter.loosed"/></option>
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-4">
                            <div class="input-group">
                                <button type="button" class="btn btn-primary"  onclick="updateTable()"><spring:message code="filter.select"/></button>
                            </div>
                        </div>
                    </form:form>

                </form:form>
                <!-- END Date & Time & Wins Filtration Form -->
                <hr><hr>
                <table class="table table-striped display" id="stakestable">
                    <thead>
                    <tr>
                        <th><spring:message code="stake.date"/></th>
                        <th><spring:message code="stake.value"/></th>
                        <th><spring:message code="stake.horse"/></th>
                        <th><spring:message code="stake.wins"/></th>
                        <th><spring:message code="stake.amount"/></th>
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
<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="modalTitle"></h2>
            </div>
            <div class="modal-body">
                <form:form class="form-horizontal" method="post" id="detailsForm" title="stake">
                    <input type="text" hidden="hidden" id="id" name="id" value="">
                    <!-- Select value of money for new stake -->
                    <div class="form-group has-feedback">
                        <label for="value" class="control-label col-xs-3"><spring:message code="stake.addForm"/>:</label>
                        <div class="col-xs-9">
                            <div id="value">
                                <%-- <input class="form-control" id="stakeValue" name="stakeValue" type="number"
                                        step="0.01" min="0" max="${availableValue}" value="${availableValue}">--%>

                            </div>
                            <span class="glyphicon form-control-feedback"></span>
                        </div>
                    </div>
                    <!-- Select horse for new stake -->
                    <div class="form-group has-feedback">
                        <label for="horse" class="control-label col-xs-3"><spring:message code="stake.horse"/>:</label>
                        <div class="col-xs-9">
                            <div id="horse">
                                <%--<select class="form-control" id="horseName" name="horseName" >
                                    <c:forEach var="horseName" items="${horsesNames}">
                                        <option value="${horseName}" ${horseName == selected ? 'selected="selected"' : ''}>${horseName}</option>
                                        &lt;%&ndash;<option value="${horseName}">${horseName}</option>&ndash;%&gt;
                                    </c:forEach>
                                </select>--%>
                            </div>
                            <span class="glyphicon form-control-feedback"></span>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal"><spring:message code="common.cancel"/></button>
                <%--<button class="btn btn-primary" type="button" onclick="save()"><fmt:message key="common.save"/></button>--%>
                <button id="save" class="btn btn-primary" type="button" onclick="checkForm()"><spring:message code="common.save"/></button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="walletInfo">
    <div class="modal-dialog m">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" style="color: red" id="walletInfoHead"><spring:message code="wallet.empty"/></h4>
            </div>
            <div class="modal-body">
                <h4><spring:message code="wallet.info"/></h4>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal"><spring:message code="common.cancel"/></button>
                <button id="fill" class="btn btn-primary" type="button" onclick=fillWallet()><spring:message code="wallet.add"/></button>
            </div>
        </div>
    </div>
</div>
<<%--div class="modal fade" id="wallet">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="walletTitle"></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="walletForm" title="stake">
                    &lt;%&ndash;<input type="text" hidden="hidden" id="id" name="id" value="">&ndash;%&gt;
                    <!-- Select value of money for new stake -->
                    <div class="form-group has-feedback">
                        <label for="value" class="control-label col-xs-3"><fmt:message key="wallet.balance"/>:</label>
                        <div class="col-xs-9">
                            <div id="cash">

                            </div>
                            <span class="glyphicon form-control-feedback"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal"><fmt:message key="common.cancel"/></button>
                &lt;%&ndash;<button class="btn btn-primary" type="button" onclick="save()"><fmt:message key="common.save"/></button>&ndash;%&gt;
                <button id="save" class="btn btn-primary" type="button" onclick="checkForm()"><fmt:message key="common.save"/></button>
            </div>
        </div>
    </div>
</div>--%>
</body>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="webjars/datetimepicker/2.4.7/build/jquery.datetimepicker.full.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript" src="resources/js/stakeDatatables.js"></script>
<script type="text/javascript">
    var edit_title = '<spring:message code="stake.edit"/>';
    var add_title = '<spring:message code="stake.add"/>';

    window.setInterval(function () {
        ajaxCallStation();  //calling every 5 seconds
        checkStatus();
    }, 5000);

    function ajaxCallStation() {
        $.get(ajaxWalletsUrl + 'station', function (data) {
            $("#station").html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Сумма ставок: ' + data.toFixed(2));
        });
    }
</script>
</html>
