package br.com.compraki.service;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public int erro;

    public String aux;

    public NegocioException() {

    }

    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(String message, int erro) {
        super(message);
        this.erro = erro;
    }

    public NegocioException(String message, int erro, String aux) {
        super(message);
        this.erro = erro;
        this.aux = aux;
    }

    public int getErro() {
        return erro;
    }

    public void setErro(int erro) {
        this.erro = erro;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

}