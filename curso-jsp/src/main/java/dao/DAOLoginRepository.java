package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	private Connection connection;
	
	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();//Iniciando conexao com o banco sempre que chamar o construtor
	}
	
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		//UPPER -> Função sql do postgres que converte uma string escrita em Minusculo para Maiusculo
		String sql = "SELECT * FROM model_login WHERE UPPER(login)= UPPER(?) AND UPPER(senha)= UPPER(?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultado = statement.executeQuery();
		
		//Se tiver resultado o codigo abaixo será executado
		if(resultado.next()) {
			return true; //Autenticado
			
		} else {
			return false; //Não autenticado
			
		}
			
	}
	
}
