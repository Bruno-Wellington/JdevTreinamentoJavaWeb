package testeconexaojdbc;

import java.util.List;

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
	
	@Test
	public void initListar() {
		
		UserPosDAO userPosDAO = new UserPosDAO();
		
		try {
			List<Userposjava> list = userPosDAO.listar();
			
			for (Userposjava userposjava : list) {
				System.out.println(userposjava);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	@Test
	public void initBuscarId() {
		
		UserPosDAO userPosDAO = new UserPosDAO();
		
		try {
			Userposjava userposjava = userPosDAO.buscarId(6L);
			System.out.println(userposjava);	
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	@Test
	public void initAtualizar() {	
		try {
			//DAO
			UserPosDAO userPosDAO = new UserPosDAO();
			//Model
			Userposjava userposjava = userPosDAO.buscarId(5L);
			
			userposjava.setNome("alterado com metodo Atualizar");
			userPosDAO.atualizar(userposjava);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
}
