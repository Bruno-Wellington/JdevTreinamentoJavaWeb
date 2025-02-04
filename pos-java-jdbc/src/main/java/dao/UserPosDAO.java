package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.Userposjava;

public class UserPosDAO {

	private Connection connection;
	
	//Start da conecção com banco de dados
	public UserPosDAO() {
		connection = SingleConnection.getConnection();
		
	}
	
	//Metodo para salvar um novo usuario
	public void salvar(Userposjava userposjava) {
		try {
			//Instrução SQL.
			String sql = "INSERT INTO userposjava (id, nome, email) VALUES (?,?,?)";
			
			//PreparedStatement é o metodo que faz o insert sql.
			PreparedStatement insert = connection.prepareStatement(sql);
			
			//No insert primeiro informamos a posição e depois o valor do campo, a posição é refetente a sequencia no insert into acima.
			insert.setLong(1, userposjava.getId());
			insert.setString(2, userposjava.getNome());
			insert.setString(3, userposjava.getEmail());			
			//execute pega o sql prepara ele atraves dos parametros(posição, valor) passados e executa.
			insert.execute();
			//salva os dados no banco.
			connection.commit();
			
		} catch (Exception e) {
			try {
				//rollback() se der algum erro reverte a operação no banco de dados.
				connection.rollback();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
				
			}
			
			e.printStackTrace();
			
		}		
	}
	
	//Metodo que retorna uma lista de usuarios
	public List<Userposjava> listar () throws Exception{
		//Lista.
		List<Userposjava> list = new ArrayList<Userposjava>();
		//Codigo sql.
		String sql = "SELECT * FROM userposjava";
		
		//Preparando o sql.
		PreparedStatement statement = connection.prepareStatement(sql);
		//Executando no banco de dados.
		ResultSet resultado = statement.executeQuery();
		
		//Enquanto o metodo next() retorna True o codigo abaixo será executado:
		while(resultado.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			
			list.add(userposjava);
		}
		
		return list;
	}
	
	//Metodo que retorna um usuario por id
	public Userposjava buscarId(Long id) throws Exception {
		
		Userposjava userposjava = new Userposjava();
		
		String sql = "SELECT * FROM userposjava where id = " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			
		}
		
		return userposjava;
	}
	
}
