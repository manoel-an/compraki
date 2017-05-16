package br.com.compraki.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Esta classe está embutida a classe telefone, ou seja, está em objetos
 * diferentes, mas nesse caso está em outra tabela se relacionando pelo
 * ElementCollection.
 */
@Embeddable
public class Telefone {

    @Column(name = "numero_um")
    private String numeroUm;

    @Column(name = "numero_dois")
    private String numeroDois;

    public String getNumeroUm() {
        return numeroUm;
    }

    public void setNumeroUm(String numeroUm) {
        this.numeroUm = numeroUm;
    }

    public String getNumeroDois() {
        return numeroDois;
    }

    public void setNumeroDois(String numeroDois) {
        this.numeroDois = numeroDois;
    }

}
