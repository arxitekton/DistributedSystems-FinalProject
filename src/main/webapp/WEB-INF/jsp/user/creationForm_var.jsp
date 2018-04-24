<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/user.form.js"></script>
</head>
<body>
    <div class="page-header">
        <h1><spring:message code="label.user.registration.page.title"/></h1>
    </div>

    <div class="panel panel-default">
        <div class="panel-body">
            <div class="registerForm">
				
				<div class="alert alert-success creation-success" hidden>
				    <strong>Success!</strong> Successful account creation.
				</div>
				
				<div class="alert alert-warning creation-warning" hidden>
				  <strong>Warning!</strong> Unsuccessful account creation.
				</div>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="row">
                    <div id="form-group-firstName" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <label class="control-label" for="user-firstName"><spring:message code="label.user.firstName"/>:</label>
                        <input id="user-firstName" name="firstName" type="text" class="form-control"/>
                    </div>
                </div>

                <div class="row">
                    <div id="form-group-lastName" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <label class="control-label" for="user-lastName"><spring:message code="label.user.lastName"/>:</label>
                        <input id="user-lastName" name="lastName" type="text" class="form-control"/>
                    </div>
                </div>

                <div class="row">
                    <div id="form-group-email" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <label class="control-label" for="user-email"><spring:message code="label.user.email"/>:</label>
                        <input id="user-email" name="email" type="email" class="form-control"/>
                    </div>
                </div>
 
                <div class="row">
                    <div id="form-group-phone" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <label class="control-label" for="user-phone"><spring:message code="label.user.phone"/>:</label>
                        <input id="user-phone" name="phone" type="text" class="form-control"/>
                    </div>
                </div>
  
                <div class="row">
                    <div id="form-group-password" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <label class="control-label" for="user-password"><spring:message code="label.user.password"/>:</label>
                        <input id="user-password" name="password" type="password" class="form-control"/>
                    </div>
                </div>
  
                <div class="row">
                    <div id="form-group-passwordVerification" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <label class="control-label" for="user-passwordVerification"><spring:message code="label.user.passwordVerification"/>:</label>
                        <input id="user-passwordVerification" name="passwordVerification" type="password" class="form-control"/>
                    </div>
                </div>
              
                <div class="row">
                    <div class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <button type="submit" class="btn btn-default"><spring:message code="label.user.registration.submit.button"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>


<script type="text/javascript">
	
$(document).ready(function() {

    $('.btn').on('click', function() {

		var firstNameField = $(this).parents('.registerForm').find('[name="firstName"]');
		var lastNameField = $(this).parents('.registerForm').find('[name="lastName"]');
		var emailField = $(this).parents('.registerForm').find('[name="email"]');
		var phoneField = $(this).parents('.registerForm').find('[name="phone"]');
		var passwordField = $(this).parents('.registerForm').find('[name="password"]');
		var passwordVerificationField = $(this).parents('.registerForm').find('[name="passwordVerification"]');
		
		var userAccountData = {};
		
		userAccountData.firstName = firstNameField.val();
		userAccountData.lastName = lastNameField.val();
		userAccountData.email = emailField.val();
		userAccountData.phone = phoneField.val();
		userAccountData.password = passwordField.val();
		userAccountData.passwordVerification = passwordVerificationField.val();
		
		
		console.log("attempt to create new user: " + firstNameField.val());

        $.ajax({
            method: 'POST',
			url: "/user/create",
			data: userAccountData

        }).done(function(data) {
			  
            console.log(data);
            
			firstNameField.val('');
			lastNameField.val('');
			emailField.val('');
			phoneField.val('');
			passwordField.val('');
			passwordVerificationField.val('');
			
			$('.creation-success').show();
			$('.alert-warning').hide();
              
        }).fail(function(jqXHR, textStatus) {
			console.log(jqXHR);
			console.log(textStatus);
			  
			alert(jqXHR.responseJSON.message);
			$('.alert-success ').hide();
 			$('.creation-warning').show();
             
        });

    });

});

</script>

</body>
</html>
