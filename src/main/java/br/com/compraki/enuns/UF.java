package br.com.compraki.enuns;

public enum UF {
    AC("Acre", "AC", "Rio Branco", RegiaoBrasil.NORTE),
    AL("Alagoas", "AL", "Maceió", RegiaoBrasil.NORDESTE),
    AP("Amapá", "AP", "Macapá", RegiaoBrasil.NORTE),
    AM("Amazonas", "AM",  "Manaus", RegiaoBrasil.NORTE),
    BA("Bahia", "BA", "Salvador", RegiaoBrasil.NORDESTE),
    CE("Ceará", "CE", "Fortaleza", RegiaoBrasil.NORDESTE),
    DF("Distrito Federal", "DF", "Brasília", RegiaoBrasil.CENTRO_OESTE),
    ES("Espírito Santo", "ES", "Vitória", RegiaoBrasil.SUDESTE),
    GO("Goiás", "GO", "Goiânia", RegiaoBrasil.CENTRO_OESTE),
    MA("Maranhão", "MA", "São Luís", RegiaoBrasil.NORDESTE),
    MT("Mato Grosso", "MT", "Cuiabá", RegiaoBrasil.CENTRO_OESTE),
    MS("Mato Grosso do Sul", "MS", "Campo Grande", RegiaoBrasil.CENTRO_OESTE),
    MG("Minas Gerais", "MG", "Belo Horizonte", RegiaoBrasil.SUDESTE),
    PA("Pará", "PA", "Belém", RegiaoBrasil.NORTE),
    PB("Paraíba", "PB", "João Pessoa", RegiaoBrasil.NORDESTE),
    PR("Paraná", "PR", "Curitiba", RegiaoBrasil.SUL),
    PE("Pernambuco", "PE", "Recife", RegiaoBrasil.NORDESTE),
    PI("Piauí", "PI", "Teresina", RegiaoBrasil.NORDESTE),
    RIJ("Rio de Janeiro", "RJ", "Rio de Janeiro", RegiaoBrasil.SUDESTE),
    RN("Rio Grande do Norte", "RN", "Natal", RegiaoBrasil.NORDESTE),
    RS("Rio Grande do Sul", "RS", "Porto Alegre", RegiaoBrasil.SUL),
    RO("Rondônia", "RO", "Porto Velho", RegiaoBrasil.NORTE),
    RR("Roraima", "RR", "Boa Vista", RegiaoBrasil.NORTE),
    SC("Santa Catarina", "SC", "Florianópolis", RegiaoBrasil.SUL),
    SP("São Paulo", "SP", "São Paulo", RegiaoBrasil.SUDESTE),
    SE("Sergipe", "SE", "Aracajú", RegiaoBrasil.NORDESTE),
    TO("Tocantins", "TO", "Palmas", RegiaoBrasil.NORTE);
    
    private final String nome;
    private final String sigla;
    private final String capital;
    private final RegiaoBrasil regiao;
     
    private UF(final String nome, final String sigla, final String capital, final RegiaoBrasil regiao) {
        this.nome = nome;
        this.sigla = sigla;
        this.capital = capital;
        this.regiao = regiao;
    }
     
    public String getNome() {
        return nome;
    }
     
    public String getSigla() {
        return sigla;
    }
     
    public String getCapital() {
        return capital;
    }
     
    public RegiaoBrasil getRegiao() {
        return regiao;
    }
    
    public static UF valueOfSigla(Object sigla) {
        if (sigla instanceof String) {
            for (UF uf : UF.values()) {
                if (uf.getSigla().equals(sigla)) {
                    return uf;
                }
            }
        }
        return null;
    }
    
    public static UF valueOfNome(Object nome) {
        if (nome instanceof String) {
            for (UF uf : UF.values()) {
                if (uf.getNome().equals(nome)) {
                    return uf;
                }
            }
        }
        return null;
    }
     
}

