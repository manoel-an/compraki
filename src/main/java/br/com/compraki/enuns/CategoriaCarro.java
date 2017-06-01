package br.com.compraki.enuns;

public enum CategoriaCarro {

	HATCH("Hatch"), SEDAN("Sedan"), MINIVAN("Minivan"), PICKUP("Pickup"), UTILITÁRIO("Utilitário");

	String descricao;

	private CategoriaCarro(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static CategoriaCarro retornaSituacao(String situacao) {
		situacao = situacao == null ? "" : situacao;
		for (CategoriaCarro value : CategoriaCarro.values()) {
			if (situacao.equals(value.toString())) {
				return value;
			}
		}
		return null;
	}
}
