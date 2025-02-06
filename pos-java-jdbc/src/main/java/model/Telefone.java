package model;

public class Telefone {
	
	private Long id;
	private String numero;
	private String tipo;
	private Long Ususuariopessoa;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Long getUsusuariopessoa() {
		return Ususuariopessoa;
	}
	public void setUsusuariopessoa(Long ususuariopessoa) {
		Ususuariopessoa = ususuariopessoa;
	}
	
	@Override
	public String toString() {
		return "Telefone [id=" + id + ", numero=" + numero + ", tipo=" + tipo + ", Ususuariopessoa=" + Ususuariopessoa
				+ "]";
	}
	
	

}
