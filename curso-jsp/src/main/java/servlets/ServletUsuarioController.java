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
				request.getRequestDispatcher("principal/cadastro-usuario.jsp").forward(request, response);
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.carregarUsuariosTela(super.getUserLogado(request));
				
				request.setAttribute("msg", "Usuários carregados!");
				/*Carregando usuarios na tela com JSTL*/
				request.setAttribute("modelLogins", modelLogins);
				request.getRequestDispatcher("/principal/cadastro-usuario.jsp").forward(request, response);
				
			} else {
				
				/*Carregando usuarios na tela com JSTL*/
				List<ModelLogin> modelLogins = daoUsuarioRepository.carregarUsuariosTela(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
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
			
			//Verificação da imagem
			if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
	            // Obtém o arquivo enviado
	            Part part = request.getPart("fileFoto");
	            
	            // Verifica se o arquivo foi enviado
	            if (part != null) {
	                // Converte o arquivo para um array de bytes
	                byte[] foto = convertPartToByteArray(part);
	                
	                // Converte o array de bytes para uma string Base64
	                String imagemBase64 = "data:" + part.getContentType().split("/")[1] + ";base64," + Base64.getEncoder().encodeToString(foto);
	                System.out.println(imagemBase64);
	                
	                // Agora você pode definir os dados no seu modelo
	                modelLogin.setFotouser(imagemBase64);
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
	
	//Função para converter imagem para um array de bytes
    private byte[] convertPartToByteArray(Part part) throws IOException {
        // Obtém o InputStream do arquivo enviado
        try (InputStream inputStream = part.getInputStream()) {
            // Converte o InputStream para um array de bytes
            return inputStream.readAllBytes(); // A partir do Java 9, você pode usar esse método direto
        }
    }

}
