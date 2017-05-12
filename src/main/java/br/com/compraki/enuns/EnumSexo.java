package br.com.compraki.enuns;

public enum EnumSexo {

    FEMININO("Feminino"), MASCULINO("Masculino");

    String descricao;

    private EnumSexo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
