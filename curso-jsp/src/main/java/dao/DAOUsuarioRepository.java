package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();//Iniciando conexao com o banco sempre que chamar o construtor
		
	}
	
	/*Metodo para salvar um novo usuario*/
	public ModelLogin salvarUsuario(ModelLogin modelLogin) throws Exception {
		
		if(modelLogin.isNovo()) { //Verifica se é um novo usuario e grava se for true
			String sql = "INSERT INTO model_login (login, senha, nome, email) VALUES (?, ?, ?, ?)";
			//Preparando sql
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, modelLogin.getLogin());
			statement.setString(2, modelLogin.getSenha());
			statement.setString(3, modelLogin.getNome());
			statement.setString(4, modelLogin.getEmail());
			statement.execute();
			connection.commit();
			
		}else { //Atualizando caso o usuario ja exista
			String sqlupdate = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id= "+modelLogin.getId()+";";
			//Preparando sql
			PreparedStatement statementUpdate = connection.prepareStatement(sqlupdate);
			statementUpdate.setString(1, modelLogin.getLogin());
			statementUpdate.setString(2, modelLogin.getSenha());
			statementUpdate.setString(3, modelLogin.getNome());
			statementUpdate.setString(4, modelLogin.getEmail());
			statementUpdate.executeUpdate();//Execute Update
			connection.commit();
			
		}
		
		//Apos gravar o usuario, será disparado esse metodo de consulta.
		return this.consultarUsuario(modelLogin.getLogin());
		
	}
	
	/*Metodo de consuta de usuario por nome*/
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
	
	/*Metodo de consuta de usuario por ID*/
	public ModelLogin consultarUsuarioId(String id) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		//Preparando sql
		String sql = "SELECT * FROM model_login WHERE id= ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
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
	
	/*Retorna uma lista de usuarios*/
	public List<ModelLogin> consultaUsuarioList(String nome) throws Exception {
		
		List<ModelLogin> listaUsuarios = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE UPPER(nome) LIKE UPPER(?) ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			listaUsuarios.add(modelLogin);
		}
		
		return listaUsuarios;
		
	}
	
	/*Retorna uma lista de usuarios para mostrar na tela. AULA 343 - Carregando os usuário em tabela com JSTL*/
	public List<ModelLogin> carregarUsuariosTela() throws Exception {
		
		List<ModelLogin> listaUsuarios = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			listaUsuarios.add(modelLogin);
		}
		
		return listaUsuarios;
		
	}
	
	/*Metodo para validar login*/
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
	
	/*Metodo para deletar um usuario*/
	public void deletarUser(String idUser) throws Exception {
		
		String sql = "DELETE FROM model_login WHERE id= ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idUser));
		statement.executeUpdate();
		connection.commit();
		
	}
}
