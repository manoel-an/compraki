package br.com.compraki.enuns;

public enum TipoPessoa {

    FISICA("Pessoa Física", "000.000.000-00"), JURIDICA("Pessoa Jurídica", "00.000.000/0000-00");

    private String descricao;
    private String mascara;

    TipoPessoa(String descricao, String mascara) {
        this.descricao = descricao;
        this.mascara = mascara;

    }

    public String getDescricao() {
        return descricao;
    }

    public String getMascara() {
        return mascara;
    }

}