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
}
