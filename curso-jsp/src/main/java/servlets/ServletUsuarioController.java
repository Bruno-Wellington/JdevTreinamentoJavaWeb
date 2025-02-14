package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;

import dao.DAOUsuarioRepository;


@WebServlet(urlPatterns = {"/ServletUsuarioController"}) //Mapeamento de URL que vem da tela
public class ServletUsuarioController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
    public ServletUsuarioController() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
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
			
			daoUsuarioRepository.salvarUsuario(modelLogin);
			request.setAttribute("msg", "Operação realizada com sucesso!");
			
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
