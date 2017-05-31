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
}
