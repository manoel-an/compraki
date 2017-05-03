package br.com.compraki.enuns;

public enum EnumSexo {

	MASCULINO("MASCULINO"), FEMININO("FEMININO"), INDEFINIDO("INDEFINIDO") ;
	
	String descricao;

	private EnumSexo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
