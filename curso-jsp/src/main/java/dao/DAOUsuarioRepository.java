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
	public ModelLogin salvarUsuario(ModelLogin modelLogin, Long userLogado) throws Exception {
		
		if(modelLogin.isNovo()) { //Verifica se é um novo usuario e grava se for true
			String sql = "INSERT INTO model_login (login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			//Preparando sql
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, modelLogin.getLogin());
			statement.setString(2, modelLogin.getSenha());
			statement.setString(3, modelLogin.getNome());
			statement.setString(4, modelLogin.getEmail());
			statement.setLong(5, userLogado);
			statement.setString(6, modelLogin.getPerfil());
			statement.setString(7, modelLogin.getSexo());
			
			statement.setString(8, modelLogin.getCep());
			statement.setString(9, modelLogin.getLogradouro());
			statement.setString(10, modelLogin.getBairro());
			statement.setString(11, modelLogin.getLocalidade());
			statement.setString(12, modelLogin.getUf());
			statement.setString(13, modelLogin.getNumero());
			statement.execute();
			connection.commit();
			
			if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
				sql = "UPDATE model_login SET fotouser= ?, extensaofotouser= ? WHERE login= ?";
				statement = connection.prepareStatement(sql);
				statement.setString(1, modelLogin.getFotouser());
				statement.setString(2, modelLogin.getExtensaofotouser());
				statement.setString(3, modelLogin.getLogin());
				statement.execute();
				connection.commit();
				
			}
			
		}else { //Atualizando caso o usuario ja exista
			String sqlupdate = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=? WHERE id= "+modelLogin.getId()+";";
			//Preparando sql
			PreparedStatement statementUpdate = connection.prepareStatement(sqlupdate);
			statementUpdate.setString(1, modelLogin.getLogin());
			statementUpdate.setString(2, modelLogin.getSenha());
			statementUpdate.setString(3, modelLogin.getNome());
			statementUpdate.setString(4, modelLogin.getEmail());
			statementUpdate.setString(5, modelLogin.getPerfil());
			statementUpdate.setString(6, modelLogin.getSexo());
			
			statementUpdate.setString(7, modelLogin.getCep());
			statementUpdate.setString(8, modelLogin.getLogradouro());
			statementUpdate.setString(9, modelLogin.getBairro());
			statementUpdate.setString(10, modelLogin.getLocalidade());
			statementUpdate.setString(11, modelLogin.getUf());
			statementUpdate.setString(12, modelLogin.getNumero());
			statementUpdate.executeUpdate();//Execute Update
			connection.commit();
			
			if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
				sqlupdate = "UPDATE model_login SET fotouser= ?, extensaofotouser= ? WHERE id= ?";
				statementUpdate = connection.prepareStatement(sqlupdate);
				statementUpdate.setString(1, modelLogin.getFotouser());
				statementUpdate.setString(2, modelLogin.getExtensaofotouser());
				statementUpdate.setLong(3, modelLogin.getId());
				statementUpdate.execute();
				connection.commit();
				
			}
			
		}
		
		//Apos gravar o usuario, será disparado esse metodo de consulta.
		return this.consultarUsuario(modelLogin.getLogin(), userLogado);
		
	}
	
	/*Metodo de consulta de usuario por nome*/
	public ModelLogin consultarUsuario(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		//Preparando sql
		String sql = "SELECT * FROM model_login WHERE UPPER(login)= UPPER(?) AND useradmin IS false";
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
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			
		}
		
		return modelLogin;
	}
	
	
	
	/*Metodo de consulta de usuario por nome e por usuario logado*/
	public ModelLogin consultarUsuario(String login, Long userLogado) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		//Preparando sql
		String sql = "SELECT * FROM model_login WHERE UPPER(login)= UPPER(?) AND useradmin IS false AND usuario_id = " + userLogado;
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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}
		
		return modelLogin;
	}
	
	
	
	/*Metodo de consuta de usuario por ID*/
	public ModelLogin consultarUsuarioId(String id, Long userLogado) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		//Preparando sql
		String sql = "SELECT * FROM model_login WHERE id= ? AND useradmin IS false AND usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		ResultSet resultado = statement.executeQuery();
		
		//Enquando tiver resultado
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}
		
		return modelLogin;
	}
	
	
	
	/*Metodo de consulta de usuario logado*/
	public ModelLogin consultarUsuarioLogado(String login) throws Exception {
		
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
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			
		}
		
		return modelLogin;
	}
	
	
	
	/*Retorna uma lista de usuarios*/
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
		
		List<ModelLogin> listaUsuarios = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE UPPER(nome) LIKE UPPER(?) AND useradmin IS false AND usuario_id = ? LIMIT 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			listaUsuarios.add(modelLogin);
		}
		
		return listaUsuarios;
		
	}
	
	
	
	public List<ModelLogin> carregarUsuariosTelaPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelLogin> listaUsuarios = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE useradmin IS false AND usuario_id = " + userLogado + " ORDER BY id OFFSET " + offset +" LIMIT 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			listaUsuarios.add(modelLogin);
		}
		
		return listaUsuarios;
		
	}
	
	
	
	/*Retorna uma lista de usuarios para mostrar na tela. AULA 343 - Carregando os usuário em tabela com JSTL*/
	public List<ModelLogin> carregarUsuariosTela(Long userLogado) throws Exception {
		
		List<ModelLogin> listaUsuarios = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * FROM model_login WHERE useradmin IS false AND usuario_id = " + userLogado + " LIMIT 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
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
		
		String sql = "DELETE FROM model_login WHERE id= ? AND useradmin IS false";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idUser));
		statement.executeUpdate();
		connection.commit();
		
	}
	
	
	
	/*Metodo para calcular o total de paginação*/
	public int totalPagina (Long userLogado) throws Exception {
		
		// Cria a string SQL que conta o número total de registros (cadastros) para o usuário especificado.
		String sql = "SELECT count(1) as total FROM model_login WHERE usuario_id= " + userLogado;
		
		// Prepara a consulta SQL para ser executada no banco de dados usando 'connection'.
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// Executa a consulta SQL e armazena o resultado na variável 'resultado' (ResultSet).
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		// Extrai o valor da coluna 'total' do ResultSet, que contém o número total de cadastros para o usuário.
		Double totalCadastros = resultado.getDouble("total");
		
		// Define a quantidade de registros a serem exibidos por página (neste caso, 5 registros por página).
		Double porPagina = 5.0;
		
		// Calcula o número de páginas dividindo o total de cadastros pela quantidade de registros por página.
		Double pagina = totalCadastros / porPagina;
		
	    // Calcula o resto da divisão do número de páginas por 2, verificando se é necessário arredondar para o próximo inteiro.
		Double resto = pagina % 2;
		
		// Se houver um valor fracionado (resto maior que 0), incrementa uma página extra.
		if (resto > 0) {
			pagina++; // Adiciona 1 à variável 'pagina' para arredondar o número de páginas para cima.
		}
		
		// Retorna o número de páginas como um valor inteiro.
		return pagina.intValue(); // Converte 'pagina' para inteiro e retorna.
	}
	
}
