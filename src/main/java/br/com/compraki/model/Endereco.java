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
	
	@NotBlank(message="Ops!Cidade está em branco")
	private String cidade;
	
	@NotBlank(message="Ops!Estado está em branco")
	private String estado;
	
	@NotBlank(message="Ops!cep está em branco")
	private String cep;
	
	//getters and setters

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

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
		

}
