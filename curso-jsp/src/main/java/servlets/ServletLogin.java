package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;

import dao.DAOLoginRepository;

@WebServlet(urlPatterns = {"/principal/ServletLogin", "/ServletLogin"}) //Mapeamento de URL que vem da tela
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
	
    public ServletLogin() {

    }

    //Recebe os dados pela URL em parametros
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	
	//Recebe os dados enviados por um formulario
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Recebendo os dados de login e senha vindos do front e armazenando em variaveis
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		try {
			//Validando se os dados de login e senha foram informados
			if(login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				
				//Setando login e senha na classe ModelLogin
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);
				
				//Validando se usuario e senha estao cadastrados
				if(daoLoginRepository.validarAutenticacao(modelLogin)) {
					
					//Mantendo o usuario logado na sessão
					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					
					//Validando a url
					if (url == null || url.equalsIgnoreCase("null")) {
						url = "principal/principal.jsp";
						
					}
					
					//Pagina para onde o usuario será Redirecionado
					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					//Comando de redirecionamento
					redirecionar.forward(request, response);
					
				}else {
					//Pagina para onde o usuario será Redirecionado caso nao passe na validação 
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					//Passando mensagem ao usuario caso nao passe na validação 
					request.setAttribute("msg", "Informe o login e senha corretamente!");
					//Comando de redirecionamento
					redirecionar.forward(request, response);
					
				}
				
			}else {
				//Pagina para onde o usuario será Redirecionado caso nao passe na validação 
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				//Passando mensagem ao usuario caso nao passe na validação 
				request.setAttribute("msg", "Informe o login e senha corretamente!");
				//Comando de redirecionamento
				redirecionar.forward(request, response);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			
		}
			
	}

}

