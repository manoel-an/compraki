package br.com.compraki.enuns;

public enum CategoriaMoto {

	SCOOTER("Scooter"), CUB("Cub"), Street("Street"), TRAIL("Trail"), CUSTOM("Custom"), NAKED("Naked"), ESPORTIVA(
			"Esportiva"), TOURING("Touring"), TRIAL("Trial"), TRICICLOS("Triciclos");

	String descricao;

	private CategoriaMoto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static CategoriaMoto retornaSituacao(String situacao) {
		situacao = situacao == null ? "" : situacao;
		for (CategoriaMoto value : CategoriaMoto.values()) {
			if (situacao.equals(value.toString())) {
				return value;
			}
		}
		return null;
	}

}
