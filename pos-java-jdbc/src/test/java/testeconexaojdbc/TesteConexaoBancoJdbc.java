package testeconexaojdbc;

import org.junit.Test;

import dao.UserPosDAO;
import model.Userposjava;

public class TesteConexaoBancoJdbc {

	@Test
	public void initBanco() {
		//SingleConnection.getConnection();
		
		//DAO
		UserPosDAO userPosDAO = new UserPosDAO();
		//Model
		Userposjava userposjava = new Userposjava();
		
		userposjava.setId(6L);
		userposjava.setNome("paulo");
		userposjava.setEmail("paulo@gmail.com");
		
		userPosDAO.salvar(userposjava);
	}
	
}
