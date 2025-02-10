<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Curso JSP</title>
</head>
<body>
	<h2>Bem Vindo ao Curso de JSP</h2>
	
	<form action="ServletLogin" method="post">	
		<table>
			<tr>
				<td><label>Login</label></td>
				<td>
					<input name="login" type="text">
				</td>
			</tr>
			
			<tr>
				<td><label>Senha</label></td>
				<td>
					<input name="senha" type="password">
				</td>	
			</tr>
			
			<tr>
				<td></td>
				<td>
					<input type="submit" value="Entrar">
				</td>		
			</tr>
		</table>	
	</form>
		<!-- Passando menssagem caso o login e senha estejam nulos ou em branco -->
		<span>${msg}</span>			
</body>
</html>