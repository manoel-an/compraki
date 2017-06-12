package br.com.compraki.dto;

import java.math.BigDecimal;

public class IntencaoDTO {

    private Long codigo;

    private String modelo;

    private String fabricante;

    private String tipoCombustivel;

    private String cidade;

    private String estado;

    private BigDecimal valor;

    public IntencaoDTO(Long codigo, String modelo, String fabricante, String tipoCombustivel, String cidade,
            String estado, BigDecimal valor) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.tipoCombustivel = tipoCombustivel;
        this.cidade = cidade;
        this.estado = estado;
        this.valor = valor;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
