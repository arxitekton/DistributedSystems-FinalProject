<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!--
<div class="page-header">
    <h1><spring:message code="label.user.login.page.title"/></h1>
</div>
-->
<sec:authorize access="isAuthenticated()">
    <p><spring:message code="text.login.page.authenticated.user.help"/></p>
    
	<p class="nav navbar-nav navbar-right navbar-text sign-in-text">
		<spring:message code="label.navigation.signed.in.as.text"/>
		<sec:authentication property="principal.username"/>
	</p>

</sec:authorize>

    <div class="panel panel-default">
        <div class="panel-body">
            <h3><spring:message code="label.login.form.title"/></h3>
            <c:if test="${param.error eq 'bad_credentials'}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <spring:message code="text.login.page.login.failed.error"/>
                </div>
            </c:if>
            <div class="loginForm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="row">
                    <div id="form-group-email" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <label class="control-label" for="user-email"><spring:message code="label.user.email"/>:</label>
                        <input id="user-email" name="username" type="text" class="form-control"/>
                    </div>
                </div>

                <div class="row">
                    <div id="form-group-password" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <label class="control-label" for="user-password"><spring:message code="label.user.password"/>:</label>
                        <input id="user-password" name="password" type="password" class="form-control"/>
                    </div>
                </div>
<!--
                <div class="row">
                    <div id="form-group-rememberme" class="form-group col-lg-4">
                        <label class="control-label" for="user-rememberme"><spring:message code="label.user.rememberme"/>:</label>
                        <input id="user-rememberme" name="remember-me" type="checkbox" class="form-control"/>
                    </div>
                </div>
-->                
                <div class="row">
                    <div class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <button type="submit" class="btn btn-default direct-login"><spring:message code="label.user.login.submit.button"/></button>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                    <a href="${pageContext.request.contextPath}/user/register"><spring:message code="label.navigation.registration.link"/></a>
                </div>
            </div>
        </div>
    </div>
    
    <div class="panel panel-default">
        <div class="panel-body">
            <h3><spring:message code="label.social.sign.in.title"/></h3>
            <div class="row social-button-row">
                <div class="col-lg-4">
                    <a href="${pageContext.request.contextPath}/auth/facebook"><button class="btn btn-facebook"><i class="icon-facebook"></i> | <spring:message code="label.facebook.sign.in.button"/></button></a>
                </div>
            </div>
            <div class="row social-button-row">
                <div class="col-lg-4">
                    <a href="${pageContext.request.contextPath}/auth/google"><button class="btn btn-google"><i class="icon-google-plus"></i> | <spring:message code="label.google.sign.in.button"/></button></a>
                </div>
            </div>
        </div>
    </div>
    



<script type="text/javascript">
	
$(document).ready(function() {

    $('.direct-login').on('click', function() {

		var username = $(this).parents('.loginForm').find('[name="username"]').val();
		var password = $(this).parents('.loginForm').find('[name="password"]').val();
		
		console.log("attempt to login from user: " + username);

        $.ajax({
            method: 'POST',
			url: "/login/authenticate",
			data: {username: username, password: password}

          }).done(function(data) {
			  
              console.log(data);
              window.location.replace("/home");
              
          }).fail(function(jqXHR, textStatus) {
			  console.log(jqXHR);
			  console.log(textStatus);
			  
              alert(jqXHR.responseJSON.message);
              
          });

    });
    
    
	$('[name="password"]').bind('keypress', function(e) {
		if(e.keyCode==13){
			$('.btn').trigger('click');
		}
	});

});

</script>
