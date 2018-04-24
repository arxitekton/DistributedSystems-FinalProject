<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<div class="header" class="page">
	    <div class="navbar navbar-default">
	        <div class="navbar-header">
	            <!-- <span class="navbar-brand">Distr Sys</span> -->
	            <ul class="menu cf">
					<li><a href="/">Home</a></li>
					<!--<li><a href="/user/create">Register</a></li>-->
					<li><a href="/login">login</a></li>
					<li><a href="/user">user</a></li>
					<!--<li><a href="/adv">adv</a></li>-->
					<!--<li><a href="#">Contacts</a></li>-->
					<!--<li><a href="#">About Us</a></li>-->
				</ul>
	            
	        </div>
	        <div class="collapse navbar-collapse navbar-ex1-collapse">        
	            <ul class="nav navbar-nav navbar-right">					                     
	                <sec:authorize access="isAuthenticated()">
	                    <li>
	                        <form action="/logout" method="POST">
	                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	                            <button type="submit" class="btn btn-default navbar-btn">
	                                Logout
	                            </button>
	                        </form>
	                    </li>
	                </sec:authorize>
	            </ul>
	        </div><!-- /.navbar-collapse -->
	    </div>
