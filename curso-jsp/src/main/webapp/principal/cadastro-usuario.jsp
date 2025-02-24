<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<!-- Incluindo o head que separamos para reaproveitar em outras paginas -->
<jsp:include page="head.jsp"></jsp:include>

<body>
	<!-- Incluindo o theme-loader -->
	<jsp:include page="theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<!-- Incluindo a navbar -->
			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<!-- Incluindo o navbarmainmenu -->
					<jsp:include page="navbarmainmenu.jsp"></jsp:include>

					<div class="pcoded-content">

						<!-- Incluindo o page-header -->
						<jsp:include page="page-header.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-header">
														<h3>Cadastro de Usuário</h3>
													</div>

													<div class="card-block">
														<!-- Chama no back end a Servelt de Usuario controller -->
														<!-- Assim como servlet que anotamos com @MultipartConfig aqui no front para fazer upload o form deve conter enctype para que seja possivel o envio de dados de imagens -->
														<form class="form-material"
															enctype="multipart/form-data"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<!-- o Value esta carregando as informações do back end na hora de cadastramos um novo usuario e mantendo na tela -->
																<input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}"> 
																<span class="form-bar"></span> 
																<label class="float-label">ID</label>
															</div>
															
															<!-- Campo de imagen -->
															<div class="form-group form-default input-group-mb-4">
																<div class="input-group-prepend">
																	<img alt="Imagem User" width="70px" src="data:image/png;base64,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">
																</div>
																<input type="file" class="form-control-file" style="margin-top: 15px; margin-left: 5px;">
															</div>
															
															<div class="form-group form-default">
																<!-- o Value esta carregando as informações do back end na hora de cadastramos um novo usuario e mantendo na tela -->
																<input type="text" name="nome" id="nome" class="form-control" required="required" autocomplete="off" value="${modelLogin.nome}">
																<span class="form-bar"></span> 
																<label class="float-label">Nome</label>
															</div>
															<div class="form-group form-default">
																<!-- o Value esta carregando as informações do back end na hora de cadastramos um novo usuario e mantendo na tela -->
																<input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
																<span class="form-bar"></span> 
																<label class="float-label">E-mail (exemplo@gmail.com)</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<span class="form-bar"></span> 
																<label>Perfil</label>
															    <select class="form-control" name="perfil">
															    
															    	<option disabled="disabled">Selecione o Perfil</option>
															      
															      	<!-- Verificando o tipo do perfil para mostrar na tela -->
															      	<option value="ADMIN" <%
															      		ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
															      
															      		if(modelLogin != null && modelLogin.getPerfil().equals("ADMIN")){
															      			out.print(" ");
															      				out.print("selected=\"selected\"");
															      			out.print(" ");
															      		
															      		} %> >Admin</option>
															      	
															      	<option value="SECRETARIA" <%
															      		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
															      
															      		if(modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")){
															      			out.print(" ");
															      				out.print("selected=\"selected\"");
															      			out.print(" ");
															      		
															      		} %> >Secretaria</option>
															      													      	
															      	<option value="AUXILIAR" <%
															      		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
															      
															      		if(modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR") ){
															      			out.print(" ");
															      				out.print("selected=\"selected\"");
															      			out.print(" ");
															      		
															      		} %> >Auxiliar</option>
															    </select>
														  	</div>
														  
															<div class="form-group form-default">
																<!-- o Value esta carregando as informações do back end na hora de cadastramos um novo usuario e mantendo na tela -->
																<input type="text" name="login" id="login" class="form-control" required="required" autocomplete="off" value="${modelLogin.login}">
																<span class="form-bar"></span> 
																<label class="float-label">Login</label>
															</div>
															<div class="form-group form-default">
																<!-- o Value esta carregando as informações do back end na hora de cadastramos um novo usuario e mantendo na tela -->
																<input type="password" name="senha" id="senha" class="form-control" required="required" autocomplete="off" value="${modelLogin.senha}">
																<span class="form-bar"></span> 
																<label class="float-label">Senha</label>
															</div>
															
															<!-- Verificando o tipo do sexo do usuario para mostrar na tela -->
															<div class="form-group form-default">
														  		<label>Sexo</label><br>
														  		
														  		<input type="radio" name="sexo" checked="checked" value="MASCULINO" <%
														  			modelLogin = (ModelLogin) request.getAttribute("modelLogin");
														  		
														  			if (modelLogin != null && modelLogin.getSexo().equals("MASCULINO")){
														  				out.print(" ");
														  					out.print("checked=\"checked\"");
														  				out.print(" ");
														  				
														  			} %> >Masculino</>
														  		
														  		<input type="radio" name="sexo" value="FEMININO" <%
														  			modelLogin = (ModelLogin) request.getAttribute("modelLogin");
														  		
														  			if (modelLogin != null && modelLogin.getSexo().equals("FEMININO")){
														  				out.print(" ");
														  					out.print("checked=\"checked\"");
														  				out.print(" ");
														  				
														  			} %> >Feminino</>
														  	</div>

															<button type="button" class="btn waves-effect waves-light hor-grd btn-grd-primary" onclick="limparForm()">Novo</button>
															<button class="btn waves-effect waves-light hor-grd btn-grd-success">Salvar</button>
															<button type="button" class="btn waves-effect waves-light hor-grd btn-grd-danger" onclick="criarDelete()">Excluir</button>
															<button type="button" class="btn waves-effect waves-light hor-grd btn-grd-warning" onclick="criarDeleteComAjax()">Excluir com Ajax</button>
															<button type="button" data-toggle="modal" data-target="#ModalPesquisarUsuario" class="btn waves-effect waves-light hor-grd btn-grd-info">Pesquisar</button>
														</form>
														
														<br> <span id="msg">${msg}</span>
													</div>
												</div>
												
												<!-- Carregando os usuário em tabela com JSTL -->
												<div style="height: 300px; overflow: scroll;">
													<table class="table" id="tabelaresultadosview">
										  				<thead>
										  					<tr>
										  						<th scope="col">Id</th>
										  						<th scope="col">Nome</th>
										  						<th scope="col">Ver</th>
										  					</tr>
										  				</thead>
										  				<tbody>
										  					<c:forEach items="${modelLogins}" var="ml">
										  						<tr>
										  							<td><c:out value="${ml.id}"></c:out></td>
										  							<td><c:out value="${ml.nome}"></c:out></td>
										  							<td><a class="btn waves-effect waves-light hor-grd btn-grd-info" href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
										  						</tr>
										  					</c:forEach>		
										  				</tbody>
										  			</table>
												</div>
												
											</div>
										</div>
										<!-- Page-body end -->
									</div>
									<div id="styleSelector"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="ModalPesquisarUsuario" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisar Usuários</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="input-group mb-3">
  						<input type="text" class="form-control" id="nomeBusca" placeholder="Digite o nome do usuário" aria-label="nome" aria-describedby="basic-addon2">
  						<div class="input-group-append">
    						<button class="btn waves-effect waves-light hor-grd btn-grd-success" type="button" onclick="buscarUsuario()">Buscar</button>
  						</div>
					</div>
					<!-- overflow: scroll - adiciona uma barra de rolagem dentro da div da tabela -->
					<div style="height: 300px; overflow: scroll;">
						<table class="table" id="tabelaresultados">
	  						<thead>
	  							<tr>
	  								<th scope="col">Id</th>
	  								<th scope="col">Nome</th>
	  								<th scope="col">Ver</th>
	
	  							</tr>
	  						</thead>
	  						<tbody>
	  							
	  						</tbody>
	  					</table>
					</div>			
					<!-- Mostra o total de usuarios encontrados -->
	  				<span id="totalResultados"></span>		
				</div>			
				<div class="modal-footer">
					<button type="button" class="btn waves-effect waves-light hor-grd btn-grd-danger" data-dismiss="modal">Fechar</button>	
				</div>
			</div>
		</div>
	</div>

	<!-- Incluindo o javascriptfile que separamos para reaproveitar em outras paginas -->
	<jsp:include page="javascriptfile.jsp"></jsp:include>

	<!-- Funções JavaScript -->
	<script type="text/javascript">
	
		/*Ver na tela as informalções do usuario que foi pesquisado*/
		function verEditar(id) {
			
			var urlAction = document.getElementById('formUser').action;
			
			/*Redirecionamento com javascript*/
			window.location.href = urlAction + '?acao=buscarEditar&id=' + id;
			
		}
	
	
		/*Buscar usuario por nome com Ajax*/
		function buscarUsuario() {
			
			var nomeBusca = document.getElementById('nomeBusca').value;
			
			/*Validando. Tem que ter valor para buscar no banco*/
			if(nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') {
				
				var urlAction = document.getElementById('formUser').action;
				
				$.ajax({

					method : "get",
					url : urlAction,
					data : "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
					success : function(response) {
						/*Convertendo a resposta em json*/
						var json = JSON.parse(response);
						
						/*Removendo todas a linhas antes de listar a busca*/
						$('#tabelaresultados > tbody > tr').remove();
						
						/*Criando os td de acordo com os usuarios encontrados na consulta*/
						for(var p = 0; p < json.length; p++){
							/*Append => adiciona o que estiver dentro dos ()*/
							$('#tabelaresultados > tbody').append('<tr><td>' + json[p].id + '</td> <td>' + json[p].nome + '</td> <td> <button onclick="verEditar(' + json[p].id + ')" class="btn waves-effect waves-light hor-grd btn-grd-info"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" width="16" height="16"><path d="M8 2c1.981 0 3.671.992 4.933 2.078 1.27 1.091 2.187 2.345 2.637 3.023a1.62 1.62 0 0 1 0 1.798c-.45.678-1.367 1.932-2.637 3.023C11.67 13.008 9.981 14 8 14c-1.981 0-3.671-.992-4.933-2.078C1.797 10.83.88 9.576.43 8.898a1.62 1.62 0 0 1 0-1.798c.45-.677 1.367-1.931 2.637-3.022C4.33 2.992 6.019 2 8 2ZM1.679 7.932a.12.12 0 0 0 0 .136c.411.622 1.241 1.75 2.366 2.717C5.176 11.758 6.527 12.5 8 12.5c1.473 0 2.825-.742 3.955-1.715 1.124-.967 1.954-2.096 2.366-2.717a.12.12 0 0 0 0-.136c-.412-.621-1.242-1.75-2.366-2.717C10.824 4.242 9.473 3.5 8 3.5c-1.473 0-2.825.742-3.955 1.715-1.124.967-1.954 2.096-2.366 2.717ZM8 10a2 2 0 1 1-.001-3.999A2 2 0 0 1 8 10Z"></path></svg></button> </td></tr>');
							
						}
						
						document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
					}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro ao buscar usuário por nome: ' + xhr.responseText);

				});
			}
		}
	
		/*Tem que ter o Jquery no projeto para funcionar*/
		function criarDeleteComAjax() {

			if (confirm('Deseja realmente excluir os dados?')) {

				var urlAction = document.getElementById('formUser').action;
				var idUser = document.getElementById('id').value;

				$.ajax({

					method : "get",
					url : urlAction,
					data : "id=" + idUser + '&acao=deletarajax',
					success : function(response) {

						limparForm(); //Limpando o formulario apos a exclusão
						document.getElementById('msg').textContent = response;
					}

				}).fail(function(xhr, status, errorThrown) {
					alert('Erro ao deletar usuário por id: ' + xhr.responseText);

				});
			}
		}

		function criarDelete() {
			
			if (confirm('Deseja realmente excluir os dados')) {

				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();

			}
		}

		function limparForm() {
			/*Retorna os elementos html dentro do form*/
			var elementos = document.getElementById("formUser").elements;

			for (p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
		}
	</script>
</body>

</html>
