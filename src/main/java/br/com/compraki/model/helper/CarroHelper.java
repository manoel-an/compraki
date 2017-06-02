package br.com.compraki.model.helper;

import java.util.List;

import com.google.gson.Gson;

import br.com.compraki.model.carro.Acessorio;

public class CarroHelper {

    private Gson parser;

    private Boolean hasErrors;

    private Boolean erroMarca;

    private Long marca;

    private Boolean erroModelo;

    private String modelo;

    private Boolean erroCategoria;

    private String categoria;

    private Boolean erroCor;

    private Long cor;

    private Boolean erroAcessorios;

    private String acessorios;

    private Gson getParser() {
        if (parser == null) {
            parser = new Gson();
        }
        return parser;
    }

    public Boolean getHasErrors() {
        if (hasErrors == null) {
            hasErrors = Boolean.FALSE;
        }
        return hasErrors;
    }

    public void setHasErrors(Boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Boolean getErroMarca() {
        return erroMarca;
    }

    public void setErroMarca(Boolean erroMarca) {
        this.erroMarca = erroMarca;
    }

    public Long getMarca() {
        return marca;
    }

    public void setMarca(Long marca) {
        this.marca = marca;
    }

    public Boolean getErroModelo() {
        return erroModelo;
    }

    public void setErroModelo(Boolean erroModelo) {
        this.erroModelo = erroModelo;
    }

    public Boolean getErroCategoria() {
        return erroCategoria;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setErroCategoria(Boolean erroCategoria) {
        this.erroCategoria = erroCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getErroCor() {
        return erroCor;
    }

    public void setErroCor(Boolean erroCor) {
        this.erroCor = erroCor;
    }

    public Long getCor() {
        return cor;
    }

    public void setCor(Long cor) {
        this.cor = cor;
    }

    public Boolean getErroAcessorios() {
        return erroAcessorios;
    }

    public void setErroAcessorios(Boolean erroAcessorios) {
        this.erroAcessorios = erroAcessorios;
    }

    public String getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(String acessorios) {
        this.acessorios = acessorios;
    }

    public String getAcessoriosJSON(List<Acessorio> acessorios) {
        return getParser().toJson(acessorios);
    }
}
