<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="org.springframework.security.taglibs.*"%>
<%@page import="org.springframework.security.core.*" %>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Démonstration sécurité Spring </title>
    <style type="text/css">
       
    </style>
</head>
<body>



<div class="profil">
Role :  <%=session.getAttribute("libelleProfil") %>
</div>



<sec:authorize  ifAnyGranted="ROLE_ADMIN">
<c:redirect url="/Pages/administrateur/indexgestionadmin.xhtml"/>
</sec:authorize>

<sec:authorize ifAnyGranted="ROLE_GESTION">
<c:redirect url="/Pages/gestionClient/indexgestion.xhtml"/>
</sec:authorize>


<sec:authorize ifAnyGranted="ROLE_CAISSIER">
<c:redirect url="/Pages/caisse/indexgestcaisse.xhtml"/>
</sec:authorize>




</body>
</html> 