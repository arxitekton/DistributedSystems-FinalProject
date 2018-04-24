<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
	<div class="page">
    <div>
            <sec:authorize access="isAuthenticated()">
                <p class="nav navbar-nav navbar-right navbar-text sign-in-text">
                    <spring:message code="label.navigation.signed.in.as.text"/>
                    <sec:authentication property="principal.username"/>
                </p>
            </sec:authorize>
    </div>
    <div class="content">
        <div id="view-holder">
            <sitemesh:write property="body"/>
        </div>
    </div>
</div>

<div class="page-header">
	<sec:authorize access="isAuthenticated()">
		<h1><spring:message code="label.homepage.title"/> <sec:authentication property="principal.firstName"/> <sec:authentication property="principal.lastName"/></h1>
    </sec:authorize>

</div>
<div>
    <sec:authorize access="isAuthenticated()">
		<p><spring:message code="text.homepage.greeting"/></p>
		Your username is <sec:authentication property="principal.username"/>
    </sec:authorize>
</div>
</body>
</html>
