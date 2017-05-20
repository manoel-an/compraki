package br.com.compraki.model.helper;

import java.time.LocalDate;

import br.com.compraki.enuns.EnumSexo;

public class PessoaHelper {

	private Boolean erroNome;

	private String nome;

	private Boolean erroRazaoSocial;

	private String razaoSocial;

	private Boolean erroDataNascimento;

	private LocalDate dataNascimento;

	private Boolean erroCpf;

	private String cpf;

	private Boolean erroCnpj;

	private String cnpj;

	private Boolean erroNomeFantasia;

	private String nomeFantasia;

	private String inputTipoPessoa;

	private String apelido;

	private EnumSexo sexo;

	private Boolean hasError;

	private String senha;

	private String confirmaSenha;

	private Boolean hasCnpjCpj;

	public Boolean getErroNome() {
		return erroNome;
	}

	public void setErroNome(Boolean erroNome) {
		this.erroNome = erroNome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getErroRazaoSocial() {
		return erroRazaoSocial;
	}

	public void setErroRazaoSocial(Boolean erroRazaoSocial) {
		this.erroRazaoSocial = erroRazaoSocial;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Boolean getErroDataNascimento() {
		return erroDataNascimento;
	}

	public void setErroDataNascimento(Boolean erroDataNascimento) {
		this.erroDataNascimento = erroDataNascimento;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Boolean getErroCpf() {
		return erroCpf;
	}

	public void setErroCpf(Boolean erroCpf) {
		this.erroCpf = erroCpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getErroCnpj() {
		return erroCnpj;
	}

	public void setErroCnpj(Boolean erroCnpj) {
		this.erroCnpj = erroCnpj;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Boolean getErroNomeFantasia() {
		return erroNomeFantasia;
	}

	public void setErroNomeFantasia(Boolean erroNomeFantasia) {
		this.erroNomeFantasia = erroNomeFantasia;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getInputTipoPessoa() {
		if (inputTipoPessoa == null) {
			inputTipoPessoa = "inputPessoaFISICA";
		}
		return inputTipoPessoa;
	}

	public void setInputTipoPessoa(String inputTipoPessoa) {
		this.inputTipoPessoa = inputTipoPessoa;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public EnumSexo getSexo() {
		return sexo;
	}

	public void setSexo(EnumSexo sexo) {
		this.sexo = sexo;
	}

	public Boolean getHasError() {
		if (hasError == null) {
			hasError = Boolean.FALSE;
		}
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public Boolean getHasCnpjCpj() {
		if (hasCnpjCpj == null) {
			hasCnpjCpj = Boolean.FALSE;
		}
		return hasCnpjCpj;
	}

	public void setHasCnpjCpj(Boolean hasCnpjCpj) {
		this.hasCnpjCpj = hasCnpjCpj;
	}

}
