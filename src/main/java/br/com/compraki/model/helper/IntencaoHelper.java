package br.com.compraki.model.helper;

import java.util.List;

import com.google.gson.Gson;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Cor;
import br.com.compraki.model.veiculo.Acessorio;

public class IntencaoHelper {

	private Gson parser;

	private Boolean hasErrors;

	private TipoVeiculo tipoVeiculo;

	private Boolean erroMarca;

	private Long marca;

	private Boolean erroModelo;

	private Long modelo;

	private Boolean erroCores;

	private String cores;

	private Boolean erroAcessorios;

	private String acessorios;

	private Gson getParser() {
		if (parser == null) {
			parser = new Gson();
		}
		return parser;
	}

	public Boolean getHasErrors() {
		if (hasErrors == null) {
			hasErrors = Boolean.FALSE;
		}
		return hasErrors;
	}

	public void setHasErrors(Boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public TipoVeiculo getTipoVeiculo() {
		if (tipoVeiculo == null) {
			tipoVeiculo = TipoVeiculo.CARRO;
		}
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public Boolean getErroMarca() {
		return erroMarca;
	}

	public void setErroMarca(Boolean erroMarca) {
		this.erroMarca = erroMarca;
	}

	public Long getMarca() {
		return marca;
	}

	public void setMarca(Long marca) {
		this.marca = marca;
	}

	public Boolean getErroModelo() {
		return erroModelo;
	}

	public void setErroModelo(Boolean erroModelo) {
		this.erroModelo = erroModelo;
	}

	public Long getModelo() {
		return modelo;
	}

	public void setModelo(Long modelo) {
		this.modelo = modelo;
	}

	public Boolean getErroCores() {
		return erroCores;
	}

	public void setErroCores(Boolean erroCores) {
		this.erroCores = erroCores;
	}

	public String getCores() {
		return cores;
	}

	public void setCores(String cores) {
		this.cores = cores;
	}

	public Boolean getErroAcessorios() {
		return erroAcessorios;
	}

	public void setErroAcessorios(Boolean erroAcessorios) {
		this.erroAcessorios = erroAcessorios;
	}

	public String getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(String acessorios) {
		this.acessorios = acessorios;
	}

	public String getAcessoriosJSON(List<Acessorio> acessorios) {
		return getParser().toJson(acessorios);
	}

	public String getCoresJSON(List<Cor> cores) {
		return getParser().toJson(cores);
	}

}
