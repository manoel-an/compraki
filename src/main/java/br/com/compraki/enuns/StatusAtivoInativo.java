package br.com.compraki.enuns;

public enum StatusAtivoInativo {

	ATIVA ("Ativa"),
	INATIVA ("Inativa");
	
	private StatusAtivoInativo(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	
	
}
