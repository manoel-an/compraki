package br.com.compraki.enuns;

public enum Categoria {

    HATCH("Hatch"), SEDAN("Sedan"), MINIVAN("Minivan"), PICKUP("Pickup"), UTILITÁRIO("Utilitário");

    String descricao;

    private Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
