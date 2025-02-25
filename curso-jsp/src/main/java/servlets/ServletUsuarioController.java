package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;

@MultipartConfig //Anotação que prepara a classe para receber upLoads
@WebServlet(urlPatterns = {"/ServletUsuarioController"}) //Mapeamento de URL que vem da tela
public class ServletUsuarioController extends ServletGenericUtil {
	
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
    public ServletUsuarioController() {

    }

    /*doGet é usado para consultar e para deletar*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String acao = request.getParameter("acao");
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String idUser = request.getParameter("id");
				daoUsuarioRepository.deletarUser(idUser);
				
				/*Carregando usuarios na tela com JSTL*/
				List<ModelLogin> modelLogins = daoUsuarioRepository.carregarUsuariosTela(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Excluido com sucesso!");
				//Lista paginada
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				//Redirecionando apos excluir o usuario
				request.getRequestDispatcher("principal/cadastro-usuario.jsp").forward(request, response);
						
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				
				String idUser = request.getParameter("id");
				daoUsuarioRepository.deletarUser(idUser);
				response.getWriter().write("Excluido com sucesso!");
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				
				String nomeBusca = request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
				
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);
				
				response.getWriter().write(json);
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				
				String id = request.getParameter("id");
				
				/*Consulta no banco de dados*/
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(id, super.getUserLogado(request));
				
				/*Carregando usuarios na tela com JSTL*/
				List<ModelLogin> modelLogins = daoUsuarioRepository.carregarUsuariosTela(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Usuário em edição");
				//Redirecionando
				request.setAttribute("modelLogin", modelLogin);
				//Lista paginada
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/cadastro-usuario.jsp").forward(request, response);
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.carregarUsuariosTela(super.getUserLogado(request));
				
				request.setAttribute("msg", "Usuários carregados!");
				/*Carregando usuarios na tela com JSTL*/
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("/principal/cadastro-usuario.jsp").forward(request, response);
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				
				String idUser = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(idUser, super.getUserLogado(request));
				
				// Verifica se a foto do usuário não é nula e não está vazia
				if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					
					// Define o cabeçalho "Content-Disposition" para indicar ao navegador que o arquivo será baixado,
				    // e também define o nome do arquivo a ser baixado. O nome do arquivo é gerado com a extensão
				    // da foto do usuário.
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
					
					// Decodifica a string Base64 (que representa a imagem) e escreve o conteúdo decodificado no fluxo de saída
				    // O método split("\\,")[1] é usado para separar a parte do conteúdo Base64 após o caractere "," 
				    // (que geralmente é encontrado em dados do tipo data URL, como "data:image/png;base64,").
				    // O Base64.getDecoder().decode(...) decodifica a string para um array de bytes que representa a imagem.
					response.getOutputStream().write(Base64.getDecoder().decode(modelLogin.getFotouser().split("\\,")[1]));
					
					
				}
				
				
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				
				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.carregarUsuariosTelaPaginada(this.getUserLogado(request), offset);
				
				request.setAttribute("modelLogins", modelLogins);
				//Lista paginada
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/cadastro-usuario.jsp").forward(request, response);
				
				
			} else {
				
				/*Carregando usuarios na tela com JSTL*/
				List<ModelLogin> modelLogins = daoUsuarioRepository.carregarUsuariosTela(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				//Lista paginada
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/cadastro-usuario.jsp").forward(request, response);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();	
			//Redireciona o usuario para a pagina de erro 
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	
	
	
	
	/*doPost é usado para salvar e atualizar*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		try {
			
			String msg = "Operação realizada com sucesso!";
			
			//Capturando as informações vindas do front end por parametro
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			
			ModelLogin modelLogin = new ModelLogin();
			
			//Operação ternaria para verificar se o campo id esta preenchido ou diferente de null para converter de String para Long
			//O campo id nunca será preenchido entao sempre vem para o back end como null e o banco de dados quem seta o valor
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);
			
			
			//UPLOAD DE FOTO
			// Verifica se o tipo de conteúdo da requisição é multipart/form-data, o que indica que o usuário está enviando um 
			if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
	           
				// Obtém o arquivo enviado no formulário, com o nome do campo "fileFoto"
	            Part part = request.getPart("fileFoto");
	            
	            // Verifica se o arquivo enviado tem algum conteúdo (se o tamanho é maior que zero)
	            if (part.getSize() > 0) {
	            	
	            	// Converte o arquivo para um array de bytes utilizando um método auxiliar
	                byte[] foto = convertPartToByteArray(part);
	                
	                // Converte o array de bytes em uma string Base64, incluindo o prefixo "data:image/{extensão};base64," 
	                // para que a imagem possa ser interpretada corretamente quando for exibida ou enviada.
	                String imagemBase64 = "data:" + part.getContentType().split("/")[1] + ";base64," + Base64.getEncoder().encodeToString(foto);
	              
	                // Define a string Base64 no modelo de dados, para armazenar a imagem em formato Base64
	                modelLogin.setFotouser(imagemBase64);
	                // Define a extensão do arquivo (como "jpg", "png", etc.) no modelo de dados
	                modelLogin.setExtensaofotouser(part.getContentType().split("/")[1]);
	               
	            }
	        }
	        
			
			
			if(daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Já existe usuário cadastrado com esse login, informe outro login!";
				
			} else {
				if(modelLogin.isNovo()) {
					msg = "Gravado com sucesso!";
					
				}else {
					msg = "Atualizado com sucesso!";
					
				}
				
				modelLogin = daoUsuarioRepository.salvarUsuario(modelLogin, super.getUserLogado(request));
				
			}
			
			/*Carregando usuarios na tela com JSTL*/
			List<ModelLogin> modelLogins = daoUsuarioRepository.carregarUsuariosTela(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			
			request.setAttribute("msg", msg);
			//Lista paginada
			request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
			//Redirecionando apos salvar novo usuario
			RequestDispatcher redireciona = request.getRequestDispatcher("principal/cadastro-usuario.jsp");
			//Atributo para mostrar os dados na tela apos salvar
			request.setAttribute("modelLogin", modelLogin);
			redireciona.forward(request, response);
			
			//podemos encurtar o codigo assim
			/* request.setAttribute("modelLogin", modelLogin);
			 * request.getRequestDispatcher("principal/cadastro-usuario.jsp").forward(request, response);
			 */
				
		}catch (Exception e) {
			e.printStackTrace();	
			//Redireciona o usuario para a pagina de erro 
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}
	
	// Método que converte o conteúdo de uma parte do formulário (Part) em um array de bytes.
    private byte[] convertPartToByteArray(Part part) throws IOException {
    	
    	// Usa o try-with-resources para garantir que o InputStream será fechado automaticamente
        try (InputStream inputStream = part.getInputStream()) {
        	
        	// Lê todo o conteúdo do InputStream e retorna como um array de bytes.
            // O método readAllBytes() foi introduzido no Java 9, facilitando a leitura completa do conteúdo.
            return inputStream.readAllBytes();
        }
    }

}
