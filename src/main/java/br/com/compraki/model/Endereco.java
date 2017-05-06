package br.com.compraki.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message="Ops!Rua está em branco")
	private String rua;
	
	@NotBlank(message="Ops!Bairro está em branco")
	private String bairro;
	
	@NotBlank(message="Ops!cep está em branco")
	private String cep;
	
	@NotBlank(message="Ops!Cidade está em branco")
	private String cidade;
	
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	

}
