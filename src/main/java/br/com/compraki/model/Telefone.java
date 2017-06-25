package br.com.compraki.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Esta classe está embutida a classe telefone, ou seja, está em objetos
 * diferentes, mas nesse caso está em outra tabela se relacionando pelo
 * ElementCollection.
 */
@Embeddable
public class Telefone {

	@NotBlank(message = "O telefone é obrigatório")
	@Column(name = "numero_um")
	private String numeroUm;

	@Column(name = "numero_dois")
	private String numeroDois;

	@Transient
	private Long codigoPessoa;

	public String getNumeroUm() {
		return numeroUm;
	}

	public void setNumeroUm(String numeroUm) {
		this.numeroUm = numeroUm;
	}

	public String getNumeroDois() {
		return numeroDois;
	}

	public void setNumeroDois(String numeroDois) {
		this.numeroDois = numeroDois;
	}

	public Long getCodigoPessoa() {
		return codigoPessoa;
	}

	public void setCodigoPessoa(Long codigoPessoa) {
		this.codigoPessoa = codigoPessoa;
	}

}
