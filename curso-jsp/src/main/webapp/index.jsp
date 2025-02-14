<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login Curso Jsp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  </head>
  <style>

	form {
		position: absolute;
		top: 30%;
		left: 40%;
	}
	
	h1 {
		position: absolute;
		top: 20%;
		left: 33%;
	}
	
	span {
		position: absolute;
		top: 65%;
		left: 40%;
	}
	
  </style>
<body>
	
	<h1>Bem Vindo ao Curso de JSP</h1>
	
	<!-- Chama no back end a Servelt de login -->
	<form action="<%= request.getContextPath() %>/ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
	
		<!-- O tipo hidden oculta a informação -->
		<input type="hidden" value="<%= request.getParameter("url") %>" name="url">	
		
		
		<div class="col-md-7">
			<label class="form-label">Login</label>
			<input class="form-control" name="login" type="text" required>
			<div class="valid-feedback">
        		Ok
      		</div>
			<div class="invalid-feedback">
        		Campo obrigatorio!
      		</div>
		</div>
			
		<div class="col-md-7">
			<label class="form-label">Senha</label>
			<input class="form-control" name="senha" type="password" required>
			<div class="valid-feedback">
        		Ok
      		</div>
			<div class="invalid-feedback">
        		Campo obrigatorio!
      		</div>
		</div>
		
		<div class="col-md-7">
			<input class="btn btn-primary" type="submit" value="Acessar">
		</div>
		
		
	</form>
		<!-- Passando menssagem caso o login e senha estejam nulos ou em branco -->
		<span>${msg}</span>	
	
	<!-- Script para funcionamento do bootstrap -->	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	
	<!-- Script para validar os campos com o bootstrap -->
	<script type="text/javascript">
		// Exemplo de JavaScript inicial para desabilitar envios de formulários se houver campos inválidos
		(() => {
		  'use strict'
	
		  // Fetch all the forms we want to apply custom Bootstrap validation styles to
		  const forms = document.querySelectorAll('.needs-validation')
	
		  // Loop over them and prevent submission
		  Array.from(forms).forEach(form => {
		    form.addEventListener('submit', event => {
		      if (!form.checkValidity()) {
		        event.preventDefault()
		        event.stopPropagation()
		      }
	
		      form.classList.add('was-validated')
		    }, false)
		  })
		})()	
	</script>
</body>
</html>