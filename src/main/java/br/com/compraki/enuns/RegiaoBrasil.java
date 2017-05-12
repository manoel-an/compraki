package br.com.compraki.enuns;

public enum RegiaoBrasil {
    NORTE("Norte"), NORDESTE("Nordeste"), CENTRO_OESTE("Centro-Oeste"), SUDESTE("Sudeste"), SUL("Sul");

    private final String nome;

    private RegiaoBrasil(final String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
