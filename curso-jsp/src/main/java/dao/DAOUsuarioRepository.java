package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();//Iniciando conexao com o banco sempre que chamar o construtor
		
	}
	
	public void salvarUsuario(ModelLogin modelLogin) throws Exception {
		
		String sql = "INSERT INTO model_login (login, senha, nome, email) VALUES (?, ?, ?, ?)";
		//Preparando sql
		PreparedStatement statement = connection.prepareStatement(sql);
		//Setando sql nas variaveis
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		statement.setString(3, modelLogin.getNome());
		statement.setString(4, modelLogin.getEmail());
		statement.execute();
		connection.commit();
		
	}
	
	
}
