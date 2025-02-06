package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
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
			String sql = "INSERT INTO userposjava (nome, email) VALUES (?,?)";
			
			//PreparedStatement é o metodo que faz o insert sql.
			PreparedStatement insert = connection.prepareStatement(sql);
			
			//No insert primeiro informamos a posição e depois o valor do campo, a posição é refetente a sequencia no insert into acima.
			insert.setString(1, userposjava.getNome());
			insert.setString(2, userposjava.getEmail());			
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
	
	
	//Metodo para salvar um telefone
	public void salvarTelefone (Telefone telefone) {
		try {
			String sql = "INSERT INTO telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuariopessoa());
			insert.execute();
			connection.commit();
			
		}catch (Exception e) {
			try {
				connection.rollback();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
				
			}
			e.printStackTrace();
		}
	}
	
	
	//Metodo com inner join para consultar os telefones de um usuario
	public List<BeanUserFone> listaUserFone (Long idUser) {
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		
		String sql = "SELECT u.nome, t.numero, u.email FROM userposjava u INNER JOIN telefoneuser t ON u.id = t.usuariopessoa WHERE u.id= " + idUser;
		
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				BeanUserFone beanUserFone = new BeanUserFone();
				beanUserFone.setNome(resultado.getString("nome"));
				beanUserFone.setNumero(resultado.getString("numero"));
				beanUserFone.setEmail(resultado.getString("email"));
				
				beanUserFones.add(beanUserFone);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		return beanUserFones;
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
	
	
	//Metodo para atualizar um usuario
	public void atualizar(Userposjava userposjava) {
		
		try {
			//Codigo sql, a ? significa que vamos passar esse infromação por parametro.
			String sql = "UPDATE userposjava SET nome= ? WHERE id= " + userposjava.getId();
			
			//Preparando o sql.
			PreparedStatement update = connection.prepareStatement(sql);
			
			//No update primeiro informamos a posição e depois o valor do campo, a posição é refetente a sequencia no update into acima.
			update.setString(1, userposjava.getNome());
			update.execute();
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
	
	
	//Metodo para deletar um usuario
	public void deletar (Long id) {		
		try {
			//Codigo sql.
			String sql = "DELETE FROM userposjava WHERE id= " + id;
			
			//Preparando o sql.
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
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
	
}
