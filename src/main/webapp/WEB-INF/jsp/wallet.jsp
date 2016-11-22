<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.uran.gamblingstation.util.TimeUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: URAN
  Date: 11.11.2016
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account management</title>
</head>
<body>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">

            <div class="view-box">

                <table class="table table-striped display" id="walletTable">
                    <thead>
                    <tr>
                        <th><fmt:message key="wallet.balance"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tr>
                        <td>${wallet.cash}</td>
                        <%--<td><c:out value="${wallet.cash}"/></td>--%>
                        <td></td>
                    </tr>
                </table>

            </div>
        </div>
    </div>
</div>

</body>
</html>
