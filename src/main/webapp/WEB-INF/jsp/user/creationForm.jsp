<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/user.form.js"></script>

<div class="page-header">
    <h1><spring:message code="label.user.registration.page.title"/></h1>
</div>

        <div class="panel panel-default">
            <div class="panel-body">
				
								
				<div class="alert alert-success creation-success" hidden>
				    <strong>Success!</strong> Account was successfully created.
				</div>
				
				<div class="alert alert-warning creation-warning" hidden>
				  <strong>Warning!</strong> Unsuccessful account creation.
				</div>


                <!-- 
                    Ensure that when the form is submitted, a POST request is send to url
                    '/user/create'.
                -->
                <form:form commandName="user" enctype="utf8" role="form" >
                    <!-- Add CSRF token to the request. -->
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="row">
                        <div id="form-group-firstName" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                            <label class="control-label" for="user-firstName"><spring:message code="label.user.firstName"/>:</label>
                            <!--
                                Add the firstName field to the form and ensure 
                                that validation errors are shown.
                            -->
                            <form:input id="user-firstName" path="firstName" cssClass="form-control"/>
                            <form:errors id="error-firstName" path="firstName" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="row">
                        <div id="form-group-lastName" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                            <label class="control-label" for="user-lastName"><spring:message code="label.user.lastName"/>:</label>
                            <!--
                                Add the lastName field to the form and ensure
                                that validation errors are shown.
                            -->
                            <form:input id="user-lastName" path="lastName" cssClass="form-control"/>
                            <form:errors id="error-lastName" path="lastName" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="row">
                        <div id="form-group-email" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                            <label class="control-label" for="user-email"><spring:message code="label.user.email"/>:</label>
                            <!-- 
                                Add the email field to the form and ensure
                                that validation errors are shown.
                            -->
                            <form:input id="user-email" type="email" required="true" path="email" cssClass="form-control"/>
                            <form:errors id="error-email" path="email" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="row">
                        <div id="form-group-phone" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                            <label class="control-label" for="user-phone"><spring:message code="label.user.phone"/>:</label>
                            <!-- 
                                Add the email field to the form and ensure
                                that validation errors are shown.
                            -->
                            <form:input id="user-phone" path="phone" cssClass="form-control"/>
                            <form:errors id="error-phone" path="phone" cssClass="help-block"/>
                        </div>
                    </div>
                    <!--
                        If the user is creating a normal user account, add password fields
                        to the form.
                    -->
                        <div class="row">
                            <div id="form-group-password" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                                <label class="control-label" for="user-password"><spring:message code="label.user.password"/>:</label>
                                <!--
                                    Add the password field to the form and ensure 
                                    that validation errors are shown.
                                -->
                                <form:password id="user-password" path="password" cssClass="form-control"/>
                                <form:errors id="error-password" path="password" cssClass="help-block"/>
                            </div>
                        </div>
                        <div class="row">
                            <div id="form-group-passwordVerification" class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-4">
                                <label class="control-label" for="user-passwordVerification"><spring:message code="label.user.passwordVerification"/>:</label>
                                <!-- 
                                    Add the passwordVerification field to the form and ensure
                                    that validation errors are shown.
                                -->
                                <form:password id="user-passwordVerification" path="passwordVerification" cssClass="form-control"/>
                                <form:errors id="error-passwordVerification" path="passwordVerification" cssClass="help-block"/>
                            </div>
                        </div>
						<div class="row">
							
							<div id="form-group-usePhoneCall" class="funkyradio-primary col-lg-4 col-md-4 col-sm-4 col-xs-4">
								<input id="user-usePhoneCall" name="usePhoneCall" type="checkbox" checked/>
								<label for="user-usePhoneCall"><spring:message code="label.user.usePhoneCall"/></label>
							</div>
							
						</div>
  
						<div class="row">
							
							<div id="form-group-useSMS" class="funkyradio-primary col-lg-4 col-md-4 col-sm-4 col-xs-4">
								<input id="user-useSMS" name="useSMS" type="checkbox" checked/>
								<label for="user-useSMS"><spring:message code="label.user.useSMS"/></label>
							</div>
							
						</div>
                        
                    <!-- Add the submit button to the form. -->
                    <button type="button" class="btn btn-primary"><spring:message code="label.user.registration.submit.button"/></button>
                </form:form>
            </div>
        </div>

<script type="text/javascript">
	
$(document).ready(function() {

    $('.btn').on('click', function() {

		var firstNameField = $(this).parents('#user').find('[name="firstName"]');
		var lastNameField = $(this).parents('#user').find('[name="lastName"]');
		var emailField = $(this).parents('#user').find('[name="email"]');
		var phoneField = $(this).parents('#user').find('[name="phone"]');
		var passwordField = $(this).parents('#user').find('[name="password"]');
		var passwordVerificationField = $(this).parents('#user').find('[name="passwordVerification"]');
		var usePhoneCallField = $(this).parents('#user').find('[name="usePhoneCall"]');
		var useSMSField = $(this).parents('#user').find('[name="useSMS"]');
		
		var userAccountData = {};
		
		userAccountData.firstName = firstNameField.val();
		userAccountData.lastName = lastNameField.val();
		userAccountData.email = emailField.val();
		userAccountData.phone = phoneField.val();
		userAccountData.password = passwordField.val();
		userAccountData.passwordVerification = passwordVerificationField.val();
		userAccountData.usePhoneCall = usePhoneCallField.val();
		userAccountData.useSMS = useSMSField.val();
		
		
		console.log("attempt to create new user: " + emailField.val());

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
