<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>About</title>
</head>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<div class="jumbotron">
    <div class="container">
        <div class="rules">
            <h2 style="color: #0000FF" align="center">ВИРТУАЛЬНЫЙ ИППОДРОМ</h2>
            <h4>
                &nbsp;&nbsp;&nbsp;<b>Виртуальный ипподром</b> - это то место, где вы можете без ущерба для кошелька реализовать
                свою тягу к азарту. Делайте ставки на понравившуюся вам лошадь и следите за результатами
                забегов - возможно вам улыбнется удача и вы выиграете!<br>
                &nbsp;&nbsp;&nbsp;Ставки принимаются начиная с начала каждого часа в течение 45 минут, например в промежутке
                10:00 - 10:45, 11:00 - 11:45 и т.д. Забеги проводятся вслед за этим в течение 10 минут,
                например с 10:45 по 10:55, в это время ставки делать нельзя.<br>
                &nbsp;&nbsp;&nbsp;Если ваша ставка выиграла,
                то на ваш кошелек перечисляется сумма выигрыша, пропорциональная сделанной ставке и общей сумме
                ставок в прошедшем забеге. Эту сумму можно использовать, чтобы делать ставки в следующем забеге.<br>
                &nbsp;&nbsp;&nbsp;Последние 5 минут часа отводятся для обслуживания - в это время администратор может добавить
                или удалить лошадей в конюшне, отредактировать список игроков и т.п. В это время ставки также не
                принимаются.<br>
                &nbsp;&nbsp;&nbsp;Если ни одна ставка не выиграла, то вся сумма ставок перечисляется на счет организатора (STATION).<br>
                &nbsp;&nbsp;&nbsp;Если есть выигравшие ставки, то вся сумма ставок перераспределяется между выигравшими участниками,
                организатор не получает ничего. Проигравшие теряют всю сумму сделанных ставок.<br>
                &nbsp;&nbsp;&nbsp;Для создания атмосферы игры на период тестирования работает симулятор, запускающий от 30 до 50 ботов,
                которые также делают ставки на случайных лошадей в случайные моменты времени.
                Виртуальный ипподром работает для вас 24 часа в сутки и 7 дней в неделю. Играйте и выигрывайте!!!
            </h4>
        </div>
        <div class="stack">
            <p>Стек технологий: <a href="http://projects.spring.io/spring-security/">Spring Security</a>,
                <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring
                    MVC</a>,
                <a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
                <a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security
                    Test</a>,
                <a href="http://hibernate.org/orm/">Hibernate ORM</a>,
                <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
                <a href="http://www.slf4j.org/">SLF4J</a>,
                <a href="https://github.com/FasterXML/jackson">Json Jackson</a>,
                <a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,
                <a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,
                <a href="http://tomcat.apache.org/">Apache Tomcat</a>,
                <a href="http://www.webjars.org/">WebJars</a>,
                <a href="http://datatables.net/">DataTables plugin</a>,
                <a href="http://ehcache.org">Ehcache</a>,
                <a href="http://www.postgresql.org/">PostgreSQL</a>,
                <a href="http://junit.org/">JUnit</a>,
                <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>,
                <a href="http://jquery.com/">jQuery</a>,
                <a href="http://ned.im/noty/">jQuery notification</a>,
                <a href="http://getbootstrap.com/">Bootstrap</a>.</p>
        </div>
    </div>
</div>
<div class="container">
    <div class="lead">
        <a href="https://github.com/uneikov/gambling_station">Java Enterprise проект</a> - <b>Bиртуальный</b>
        ипподром с регистрацией/авторизацией и интерфейсом на основе ролей (USER, ADMIN, STATION).
        Администратор может создавать/редактировать/удалять/пользователей, а пользователь - управлять своим
        профилем и ставками (дата/время, лошадь, ставка) через UI (по AJAX) и по REST интерфейсу с базовой авторизацией.
        Возможна фильтрация данных по датам и времени, при этом цвет записи таблицы ставок зависит от того, была
        ли ставка выигрышной или проигрышной.
        Весь REST интерфейс покрывается JUnit тестами, используя Spring MVC Test и Spring Security Test.
        Проект реализован с использованием технологий, полученных в ходе вебинара TopJava8.
    </div>
    <button type="button" class="btn btn-danger" onclick="history.go(-1);"><spring:message
            code="app.home"/></button><hr>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
