package br.com.compraki.enuns;

public enum EnumSexo {

	FEMININO("Feminino"), MASCULINO("Masculino"), INDEFINIDO("Indefinido");

	String descricao;

	private EnumSexo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
