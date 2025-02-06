package testeconexaojdbc;

import java.util.List;

import org.junit.Test;

import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteConexaoBancoJdbc {

	//CREATED
	@Test
	public void initBanco() {
		//SingleConnection.getConnection();
		
		//DAO
		UserPosDAO userPosDAO = new UserPosDAO();
		//Model
		Userposjava userposjava = new Userposjava();
		
		userposjava.setNome("lucas");
		userposjava.setEmail("lucas@gmail.com");
		
		userPosDAO.salvar(userposjava);
	}
	
	//Read por lista
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
	
	//Read por id
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
	
	//UPDATE
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
	
	//DELETE
	@Test
	public void initDeletar() {
		try {			
			UserPosDAO userPosDAO = new UserPosDAO();
			userPosDAO.deletar(5L);
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
	
	//INSERT de telefone
	@Test
	public void initTesteInsertTelefone() {
		Telefone telefone = new Telefone();
		telefone.setNumero("(83) 99865-1738");
		telefone.setTipo("Casa");
		telefone.setUsuariopessoa(9L);
		
		UserPosDAO userPosDAO = new UserPosDAO();
		userPosDAO.salvarTelefone(telefone);
	}
	
	
	//Read de telefone com Inner Join
	@Test
	public void initConsultaUserFone() {
		UserPosDAO userPosDAO = new UserPosDAO();
		
		List<BeanUserFone> beanUserFones = userPosDAO.listaUserFone(10L);
		
		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
			System.out.println();
			
		}
		
	}
}
