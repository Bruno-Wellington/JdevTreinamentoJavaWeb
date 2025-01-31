package conexaojdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

	private static String url = "jdbc:postgresql://localhost:5432/posjava";
	private static String user = "postgres";
	private static String password = "1234";
	private static Connection connection = null;
	
	//Sempre que o SingleConnection for invocado o metodo conectar() sera chamado.
	static {
		conectar();
		
	}
	
	//Construtor.
	public SingleConnection() {
		conectar();
		
	}
	
	//Metodo para conectar.
	private static void conectar() {
		try {
			//A conexao é feita apenas uma vez o que é aberto e fechado depois são as seções.
			if(connection == null) {
				//Carregando o driver do banco que iremos usar.
				Class.forName("org.postgresql.Driver");
				
				//Definindo as informações da conexao.
				connection = DriverManager.getConnection(url, user, password);
				
				//setAutoCommit -> Usado para nao salvar automaticamente as informações de Create, Insert, Update e Delete
				//nos quem decidimos quando isso sera feito efetivamente.
				connection.setAutoCommit(false); 
				
				System.out.println("Conectou com sucesso!");
			}
			
			
		}catch (Exception e) {
			e.printStackTrace(); //Imprime no console caso ocorra algum erro.
			
		}
	}
	
	//metodo que retorna a conexao.
	public static Connection getConnection() {
		return connection;
		
	}
}
