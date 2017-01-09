<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="fragments/headTag.jsp"/>
    <jsp:include page="fragments/bodyHeader.jsp"/>
    <link rel="stylesheet" href="webjars/datatables/1.10.12/css/dataTables.bootstrap.min.css">
</head>
<body>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="horses.title"/></h3>
            <div class="view-box">
                <a class="btn btn-sm btn-info" onclick="add()"><fmt:message key="horses.add"/></a>
                <hr>
                <table class="table table-striped display" id="horsestable">
                    <thead>
                    <tr>
                        <th><fmt:message key="horses.name"/></th>
                        <th><fmt:message key="horses.ru_name"/></th>
                        <th><fmt:message key="horses.age"/></th>
                        <th><fmt:message key="horses.wins"/></th>
                        <th><fmt:message key="horses.ready"/></th>
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
<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="modalTitle"></h2>
            </div>
            <div class="modal-body">
                <form:form class="form-horizontal" method="post" id="detailsForm" title="horse">
                    <input type="text" hidden="hidden" id="id" name="id">
                    <input type="text" hidden="hidden" id="ready" name="ready">
                    <input type="text" hidden="hidden" id="wins" name="wins">

                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3">Name</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="ruName" class="control-label col-xs-3">Name(Cyrillic)</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="ruName" name="ruName" placeholder="Cyrillic name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="age" class="control-label col-xs-3">Age</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="age" name="age" placeholder="Age">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button class="btn btn-default" type="button" data-dismiss="modal"><fmt:message key="common.cancel"/></button>
                            <button class="btn btn-primary"  type="button" onclick="save()"><fmt:message key="common.save"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var edit_title = '<fmt:message key="horses.edit"/>';
    var add_title = '<fmt:message key="horses.add"/>';
</script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript" src="resources/js/horseDatatables.js"></script>
<script>
    $(document).ready(function () {
        $('#addButton').hide();
    });
</script>
</html>
