package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;

@WebFilter(urlPatterns = {"/principal/*"})//Intercepta todas as requisições que vierem do projeto ou mapeamento (Nesse caso estamos mapeando tudo dentro da pasta principal)
public class FilterAutenticacao extends HttpFilter implements Filter {
       
	private static Connection connection; //Variavel de conexão com banco de dados
		
    public FilterAutenticacao() {
        
    }

    //Encerra os processos quando o servidor é parado
    //Destroi os processos de conexao com o banco
	public void destroy() {
		try {
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}

	//Intercepta as requisições e respostas no sistema
	//Tudo que for feito no sistema vai passar por ele, ex: validação de autenticação, commits e rolbacks de transações, validar e fazer redirecionamentos de paginas, etc...
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
			//Convertendo o request ServletRequest em req HttpServletRequest
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			//Url que esta sendo acessada
			String urlParaAutenticar = req.getServletPath();
			
			//Validando se esta logado, se nao redirecionamos para a tela de login
			if(usuarioLogado == null && 
					!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {//Usuario nao está logado
				
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return; //Para a execução e redireciona para o login
				
			}else {
				chain.doFilter(request, response);				
			}
			
			connection.commit();//Se der tudo certo, entao as informações serão commitadas no banco de dados
			
		}catch (Exception e) {
			e.printStackTrace();
			
			try {
				connection.rollback();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
				
			}
		}
	}

	//Inicia os processos ou recursos quando o servidor sobe o projeto
	//Inicia a conexão com o banco quando subir o projeto
	public void init(FilterConfig fConfig) throws ServletException {
		
		connection = SingleConnectionBanco.getConnection();//Conexão com o banco de dados
		
	}

}
