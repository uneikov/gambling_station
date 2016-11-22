<%@ page import="com.uran.gamblingstation.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<jsp:include page="fragments/i18nTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.12/css/dataTables.bootstrap.min.css">
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="races.title"/></h3>
            <div class="view-box">

                <table class="table table-striped display" id="racestable">
                    <thead>
                    <tr>
                        <th><fmt:message key="races.startRace"/></th>
                        <th><fmt:message key="races.endRace"/></th>
                        <th><fmt:message key="races.horses.list"/></th>
                        <th><fmt:message key="races.wining.horse"/></th>
                        <th><fmt:message key="races.stakes.count"/></th>
                        <th><fmt:message key="races.stakes.value"/></th>
                        <th><fmt:message key="races.stakes.amount"/></th>
                    </tr>
                    </thead>
                    <c:forEach items="${raceRows}" var="row">
                        <jsp:useBean id="row" scope="page" type="com.uran.gamblingstation.to.RaceRow"/>
                        <tr>
                        <td><%=TimeUtil.toString(row.getStart())%></td>
                        <td><%=TimeUtil.toString(row.getFinish())%></td>
                        <%-- <td>${horses}</td>--%>
                        <%-- <label for="option" class="control-label col-xs-2"><fmt:message
                                 key="filter.options_select"/></label>--%>
                        <%-- <div class="col-xs-2">
                             <select id="option" name="option" class="form-control">
                                 <option value="all"><fmt:message key="filter.all"/></option>
                                 <option value="winned"><fmt:message key="filter.winned"/></option>
                                 <option value="loosed"><fmt:message key="filter.loosed"/></option>
                             </select>
                         </div>--%>
                        <td>
                            <div class="dropdown">
                                <a id="drop1" href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">${row.en_names.get(0)}</a></li>
                                    <li><a href="#">${row.en_names.get(1)}</a></li>
                                    <li><a href="#">${row.en_names.get(2)}</a></li>
                                    <li><a href="#">${row.en_names.get(3)}</a></li>
                                    <li><a href="#">${row.en_names.get(4)}</a></li>
                                    <li><a href="#">${row.en_names.get(5)}</a></li>
                                </ul>
                            </div>
                        </td>
                        <td>${row.en_winning}</td>
                        <td>${row.count}</td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${row.stakeSum}"/>
                        </td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${row.amountSum}"/>
                        </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<%--<script>
    ajaxUrl='ajax/admin/races/';
    function getStakes(id) {
        $.get(ajaxUrl + id, function (data) {
            return data;
        });
    }
</script>--%>
</html>
