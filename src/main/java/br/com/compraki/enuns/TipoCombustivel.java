package br.com.compraki.enuns;

public enum TipoCombustivel {

	ALCOOL ("Álcool"),
	GASOLINA ("Gasolina"),
	FLEX ("Flex"),
	DIESEL("Diesel"),
	GNV("Gás Natural"),
	GASOLINA_GNV("Gasolina com GNV");
	
	private TipoCombustivel(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	
	
}
