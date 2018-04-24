<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tiles:getAsString name="title" /></title>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap-theme.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/style.css"/>

   <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

	<!-- Include one of jTable styles. -->
	<link href="/static/js/vendor/jtable.2.4.0/themes/metro/blue/jtable.min.css" rel="stylesheet" type="text/css" />

	<!-- Include jTable script file. -->
	<script src="/static/js/vendor/jtable.2.4.0/jquery.jtable.min.js" type="text/javascript"></script>
	
	<link rel="stylesheet" href="/static/lib/colorbox/example3/colorbox.css" type="text/css" media="screen" />
	<script src="/static/lib/colorbox/jquery.colorbox.js"></script>
	
	<link rel='stylesheet' href="/static/lib/swipebox/css/swipebox.min.css" media='screen' />
	<script src="/static/lib/swipebox/js/jquery.swipebox.min.js"></script>
	
	<link rel="stylesheet" href="/static/lib/Justified-Gallery/dist/css/justifiedGallery.css" type="text/css" media="all" />
	<script src="/static/vendor/Justified-Gallery/dist/js/jquery.justifiedGallery.js"></script>

    <script type="text/javascript" src="/static/js/vendor/bootstrap.js"></script>
    
    <style type="text/css">
    body {
        width: 1200px;
        margin: auto;

        padding:0px;
        height:100%;
/*        overflow: hidden;*/
        overflow-x: auto;
    }

    .page {
        min-height:100%;
        position:relative;
    }

    .header {
        padding:0px;
        width:100%;
        text-align:center;
    }

    .content {
        padding:0px;
        width:100%;
        padding-bottom:20px; /* Height of the footer element */

    }

    .body {
        margin:50px 10px 0px 250px;
    }

    .footer {
        clear:both;
        position:absolute;
        bottom:0;
        left:0;
        text-align:center;
        width:100%;
        height:20px;
    }

    </style>

</head>

<body>

    <div class="page">
        <tiles:insertAttribute name="header" />

        <div class="content">
            <tiles:insertAttribute name="body" />
        </div>

        <tiles:insertAttribute name="footer" />
    </div>

</body>

</html>
