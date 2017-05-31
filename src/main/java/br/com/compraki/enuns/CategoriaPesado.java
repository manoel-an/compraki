package br.com.compraki.enuns;

public enum CategoriaPesado {

    VUC("Veículo Urbano de Carga"), TOCO("Toco ou caminhão semi-pesado"), TRUCK("Caminhão Pesado"), CAVALO_MECANICO(
            "Caminhão Extra-pesado"), LS("Cavalo Mecânico Trucado"), DOIS_EIXOS("Carreta 2 eixos"), TRES_EIXOS(
                    "Carreta 3 eixos"), CAVALO_TRUCADO("Carreta Cavalo Trucado"), BITREM(
                            "Bitrem ou Treminhão"), RODOTREM("Rodotrem"), ONIBUS_CONVENCIONAL(
                                    "Ônibus convencional"), ONIBUS_EXECUTIVO("Ônibus executivo"), ONIBUS_LEITO(
                                            "Ônibus leito");

    String descricao;

    private CategoriaPesado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
