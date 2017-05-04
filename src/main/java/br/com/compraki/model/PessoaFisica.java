package br.com.compraki.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="pessoa_fisica")
@DiscriminatorValue("1")
public class PessoaFisica extends Pessoa {
	
	@NotBlank
	private String cpf;
	
	private String apelido;
	
	//getters and setters
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	
	

}
