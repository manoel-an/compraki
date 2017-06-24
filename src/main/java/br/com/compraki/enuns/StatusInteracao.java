package br.com.compraki.enuns;

public enum StatusInteracao {

	ACEITA ("Aceita"),
	RECUSADA ("Recusada"),
	ENVIADA ("Enviada");
	
	private StatusInteracao(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	
	
}
