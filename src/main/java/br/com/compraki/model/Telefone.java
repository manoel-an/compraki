package br.com.compraki.model;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

/**Esta classe está embutida a classe telefone, ou seja, está em objetos diferentes, mas nesse
 * caso está em outra tabela se relacionando pelo ElementCollection. */
@Embeddable
public class Telefone {

	public Telefone(){
		
	}
	
	public Telefone(String numero) {
		this.numero = numero;
	}
		
	@NotBlank(message="Ops! Você deve inserir um número de telefone")
	private String numero;
	
	//Getters and Setters
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
		
	
	
}
