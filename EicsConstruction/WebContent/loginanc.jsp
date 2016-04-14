<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GESTION EICS construction</title>
</head>
<body>

	
	<form method="post" action="j_spring_security_check">
		<div style="position: fixed; top: 0; bottom: 0; left: 0; right: 0; border: solid 1px black">
	<div style="position: absolute; width: 320px; height: 300px; top:0; bottom:0; left:0; right: 0; margin: auto;">
 	<fieldset style="background-color: threedlightshadow; border-radius: 8px 8px 8px 8px;">
		<h3 style="text-align: center; background-color: #C00; color: white;">CONNEXION</h3>
		<table style="margin-left: 0px">
			<tr>
				<td> Login:</td>
				<td><input type='text' name='j_username' value='' style="height: 20px; font-size: 15px; font-weight: lighter;">
				</td>
			</tr>
			<tr>
				<td>Mot de passe:</td>
				<td><input type='password' name='j_password' style="height: 20px; font-size: 15px; font-weight: lighter;"/>
				</td>
			</tr>
			<tr>
				<td style="width: 100px"></td>
				<td style="margin-left: 25px" colspan='2'><input name="submit" type="submit"
					value="Se connecter" />
				</td>
			</tr>			
		</table>
		</fieldset>
 	</div>
 	
 	</div> 
	</form>
</body>
</html>