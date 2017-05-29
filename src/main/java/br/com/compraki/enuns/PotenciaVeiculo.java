package br.com.compraki.enuns;

public enum PotenciaVeiculo {

	P10 ("1.0"),
	P14 ("1.4"),
	P16 ("1.6"),
	P18 ("1.8"),
	P20 ("2.0"),
	P22 ("2.2"),
	P24 ("2.4"),
	P30 ("3.0"),
	P40 ("4.0");

	private PotenciaVeiculo(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	
	
}
