package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/*Essa conexão será instanciada no Filter de FilterAutenticacao dentro do metodo init da classe*/
public class SingleConnectionBanco {

	private static String url = "jdbc:postgresql://localhost:5432/cursojsp?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "1234";
	private static Connection connection = null;
	
	static {
		conectar();//Se ouver uma chamada da classe diretamente, será conectado ao banco
	}
	
	public SingleConnectionBanco() {//Quando tiver uma instancia vai conectar com o banco
		conectar();
	}
	
	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver"); //Carrega o driver de conexão do banco postgres
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);//Para nao efetuar alterações no banco sem nosso comando
			}
						
		} catch (Exception e) {
			e.printStackTrace(); //Mostra qualquer erro na hora da conexão
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
}
