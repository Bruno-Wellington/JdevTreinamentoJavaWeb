<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
														<form class="form-material" action="<%= request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser">
														
															<input type="hidden" name="acao" id="acao" value="">
														
															<div class="form-group form-default form-static-label">
																<!-- o Value esta carregando as informações do back end na hora de cadastramos um novo usuario e mantendo na tela -->
																<input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}"> 
																<span class="form-bar"></span> 
																<label class="float-label">ID</label>
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
															
															<button type="button" class="btn waves-effect waves-light hor-grd btn-grd-primary" onclick="limparForm()">Novo</button>
															<button class="btn waves-effect waves-light hor-grd btn-grd-success">Salvar</button>
															<button type="button" class="btn waves-effect waves-light hor-grd btn-grd-danger" onclick="criarDelete()">Excluir</button>
															<button type="button" class="btn waves-effect waves-light hor-grd btn-grd-warning" onclick="criarDeleteComAjax()">Excluir com Ajax</button>
									
														</form>
														<br>
														<span id="msg">${msg}</span>
													</div>
													
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
		<!-- Incluindo o javascriptfile que separamos para reaproveitar em outras paginas -->
		<jsp:include page="javascriptfile.jsp"></jsp:include>
		
		<script type="text/javascript">
		
			/*Tem que ter o Jquery no projeto para funcionar*/
			function criarDeleteComAjax() {
				
				if(confirm('Deseja realmente excluir os dados?')){
					
					var urlAction = document.getElementById('formUser').action;
					var idUser = document.getElementById('id').value;
					
					$.ajax({
						
						method: "get",
						url: urlAction,
						data: "id=" + idUser + '&acao=deletarajax',
						success: function(response) {
							
							limparForm(); //Limpando o formulario apos a exclusão
							document.getElementById('msg').textContent = response;
						}
						
					}).fail(function(xhr, status, errorThrown) {
						alert('Erro ao deletar usuário por id: ' + xhr.responseText);
						
					});
					
				}
			}	
		
			function criarDelete() {
				
				if(confirm('Deseja realmente excluir os dados')) {
					
					document.getElementById("formUser").method = 'get';
					document.getElementById("acao").value = 'deletar';
					document.getElementById("formUser").submit();
					
				}
			}	
		
			function limparForm() {
				/*Retorna os elementos html dentro do form*/
				var elementos = document.getElementById("formUser").elements;
				
				for(p = 0; p < elementos.length; p++){
					elementos[p].value = '';
				}
			}
		</script>
</body>

</html>
