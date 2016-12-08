<%@ page import="com.uran.gamblingstation.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
            <h3><spring:message code="races.title"/></h3>
            <input hidden="hidden" id="locale" value="${pageContext.response.locale}">
            <div class="view-box">

                <table class="table table-striped display" id="racestable">
                    <thead>
                    <tr>
                        <th><spring:message code="races.start"/></th>
                        <th><spring:message code="races.finish"/></th>
                        <th><spring:message code="races.horses.list"/></th>
                        <th><spring:message code="races.wining.horse"/></th>
                        <th><spring:message code="races.stakes.count"/></th>
                        <th><spring:message code="races.stakes.value"/></th>
                        <th><spring:message code="races.stakes.wins"/></th>
                        <th><spring:message code="races.stakes.amount"/></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="webjars/datetimepicker/2.4.7/build/jquery.datetimepicker.full.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript" src="resources/js/raceDatatables.js"></script>
<script>
    $(document).ready(function(){
        $('#addButton').hide();
    });
</script>
</html>
