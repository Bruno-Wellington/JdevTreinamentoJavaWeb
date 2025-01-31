package testeconexaojdbc;

import org.junit.Test;

import conexaojdbc.SingleConnection;

public class TesteConexaoBancoJdbc {

	@Test
	public void initBanco() {
		SingleConnection.getConnection();
		
	}
	
}
