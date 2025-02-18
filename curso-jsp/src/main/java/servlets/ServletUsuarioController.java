package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;


@WebServlet(urlPatterns = {"/ServletUsuarioController"}) //Mapeamento de URL que vem da tela
public class ServletUsuarioController extends HttpServlet {
	
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
				request.setAttribute("msg", "Excluido com sucesso!");
				
				//Redirecionando apos excluir o usuario
				request.getRequestDispatcher("principal/cadastro-usuario.jsp").forward(request, response);
						
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				
				String idUser = request.getParameter("id");
				daoUsuarioRepository.deletarUser(idUser);
				response.getWriter().write("Excluido com sucesso!");
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				
				String nomeBusca = request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca);
				
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);
				
				response.getWriter().write(json);
				
			} else {
				
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
			
			ModelLogin modelLogin = new ModelLogin();
			//Operação ternaria para verificar se o campo id esta preenchido ou diferente de null para converter de String para Long
			//O campo id nunca será preenchido entao sempre vem para o back end como null e o banco de dados quem seta o valor
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			
			if(daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Já existe usuário cadastrado com esse login, informe outro login!";
				
			} else {
				if(modelLogin.isNovo()) {
					msg = "Gravado com sucesso!";
					
				}else {
					msg = "Atualizado com sucesso!";
					
				}
				
				modelLogin = daoUsuarioRepository.salvarUsuario(modelLogin);
				
			}
			
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

}
