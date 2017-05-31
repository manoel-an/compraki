package br.com.compraki.repository.filter;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Cor;
import br.com.compraki.model.carro.Fabricante;

public class CarroFilter {

	private Long codigo;

	private String modelo;

	private Fabricante fabricante;

	private Cor cor;

	private TipoVeiculo tipoVeiculo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

}