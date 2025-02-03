package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexaojdbc.SingleConnection;
import model.Userposjava;

public class UserPosDAO {

	private Connection connection;
		
	public UserPosDAO() {
		connection = SingleConnection.getConnection();
		
	}
	
	public void salvar(Userposjava userposjava) {
		try {
			//Instrução SQL.
			String sql = "insert into userposjava (id, nome, email) values (?,?,?)";
			
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
}
