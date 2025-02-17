package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();//Iniciando conexao com o banco sempre que chamar o construtor
		
	}
	
	public ModelLogin salvarUsuario(ModelLogin modelLogin) throws Exception {
		
		String sql = "INSERT INTO model_login (login, senha, nome, email) VALUES (?, ?, ?, ?)";
		//Preparando sql
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		statement.setString(3, modelLogin.getNome());
		statement.setString(4, modelLogin.getEmail());
		statement.execute();
		connection.commit();
		
		//Apos gravar o usuario, será disparado esse metodo de consulta.
		return this.consultarUsuario(modelLogin.getLogin());
		
	}
	
	public ModelLogin consultarUsuario(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		//Preparando sql
		String sql = "SELECT * FROM model_login WHERE UPPER(login)= UPPER(?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		ResultSet resultado = statement.executeQuery();
		
		//Enquando tiver resultado
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
		}
		
		return modelLogin;
	}
	
	public boolean validarLogin(String login) throws Exception {
		
		String sql = "SELECT COUNT(1) > 0 AS existe FROM model_login WHERE UPPER(login) = UPPER(?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		ResultSet resultado = statement.executeQuery();
		
		if(resultado.next()) {
			return resultado.getBoolean("existe");
		}
		
		return false;
	
	}
}
