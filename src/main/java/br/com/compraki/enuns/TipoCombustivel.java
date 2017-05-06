package br.com.compraki.enuns;

public enum TipoCombustivel {

	ALCOOL ("Álcool"),
	GASOLINA ("Gasolina"),
	FLEX ("Flex");
	
	private TipoCombustivel(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	
	
}
