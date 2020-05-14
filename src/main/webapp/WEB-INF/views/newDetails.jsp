<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="show"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successs</title>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
body {
	font-family: "Times New Roman", Georgia, Serif;
	padding: 1px;
}

h1, h2, h3, h4, h5, h6 {
	font-family: "Times New Roman", Georgia, Serif;
	letter-spacing: 5px;
	padding: 10px;
	text-align: center;
	color: green;
}

* {
	box-sizing: border-box
}

/* Add padding to containers */
.container {
	padding: 0px 0 0 20px;
	font-size: 14px;
}

/* Full-width input fields */
input[type=text], input[type=password], input[type=radio], input[type=text],
	select {
	font-family: "Times New Roman", Georgia, Serif width : 100%;
	padding: 12px;
	margin: 5px 0 7px 0;
	display: inline-block;
	background: #f1f1f1;
	border-style: solid;
	border-width: thin;
	border-radius: 5px;
	font-size: 14px
}

/* input[type=text]:focus, input[type=password]:focus, input[type=radio]:focus {
font-family: "Times New Roman",Georgia, Serif
  background-color: #ddd;
  outline: none;
} */

/* Overwrite default styles of hr */
hr {
	border: 1px solid #f1f1f1;
	margin-bottom: 20px;
}

/* Set a style for the submit/register button */
.registerbtn {
	background-color: #4CAF50;
	color: white;
	padding: 16px 20px;
	margin: 7px 0 0 20px;
	border: none;
	cursor: pointer;
	width: 100%;
	opacity: 0.9;
	font-size: 14px;
}

.registerbtn:hover {
	opacity: 1;
}

/* Add a blue text color to links */
a {
	color: dodgerblue;
}
</style>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">


<style type="text/css">
.error {
	color: red;
	font-family: "Times New Roman", Georgia, Serif;
	font-size: 20px;
}
</style>


</head>
<body>

	<div class="w3-top">
		<div class="w3-bar w3-white w3-padding w3-card"
			style="letter-spacing: 4px;">
			<div class="w3-container w3-black">
				<a href="/com.xworks.commonmodule/" class="w3-bar-item w3-button">X-workz
					ODC</a>
				<!-- Right-sided navbar links. Hide them on small screens -->
				<div class="w3-right w3-hide-small">

					<a href="/com.xworks.commonmodule/registerPage" method="post"
						class="w3-bar-item w3-button">Register </a> <a
						href="/com.xworks.commonmodule/loginPage" method="post"
						class="w3-bar-item w3-button">Log-In </a>
				</div>
			</div>
		</div>
	</div>

	<br>
	<br>
	<br>
	<br>

	<h3>Password Reset</h3>
	<div class="container">



		<table>
			<tr>
				<td><b>User ID :</b></td>
				<td><show:out value="${user_id}" /></td>
			</tr>

			<tr>
				<td><b>Email :</b></td>
				<td><show:out value="${email }" /></td>
			</tr>

			<tr>
				<td><b>Generate Password :</b></td>
				<td><show:out value="${newPassword }" /></td>
			</tr>
		</table>

	</div>

	<br>

	<br>
	<a href="/com.xworks.commonmodule/loginPage" method="post"
		class="registerbtn">Log In</a>
	<a href="/com.xworks.commonmodule/reset" method="post"
		class="registerbtn">Reset Password</a>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>

	<footer class="w3-center w3-light-grey w3-padding-32">
	<h3 class="w3-center">About Xworkz</h3>
	<p>
		� 2020 Copyright:<a
			href="https://www.facebook.com/xworkzdevelopmentcenter"
			title="W3.CSS" target="_blank" class="w3-hover-text-green">X-wrokz-ODC.com</a>
	</p>
	</footer>
</body>





</html>