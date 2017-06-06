package br.com.compraki.enuns;

public enum StatusIntencao {

	ACEITA ("Aceita"),
	RECUSADA ("Recusada"),
	ENVIADA ("Enviada");
	
	private StatusIntencao(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	
	
}
