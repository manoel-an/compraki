package br.com.compraki.enuns;

public enum TipoVeiculo {

	CARRO ("Carros"),
	MOTO ("Motos"),
	PESADO ("Caminhões, Ônibus e Vans"),
	UTILITARIOS("Utilitários");
	
	private TipoVeiculo(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	
	
}
