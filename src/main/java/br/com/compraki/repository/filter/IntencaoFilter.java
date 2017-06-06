package br.com.compraki.repository.filter;

import java.math.BigDecimal;
import java.util.List;

import br.com.compraki.enuns.PotenciaVeiculo;
import br.com.compraki.enuns.TipoCombustivel;
import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Cidade;
import br.com.compraki.model.Cor;
import br.com.compraki.model.veiculo.Acessorio;
import br.com.compraki.model.veiculo.Fabricante;
import br.com.compraki.model.veiculo.ModeloVeiculo;

public class IntencaoFilter {

    private Long codigo;

    private BigDecimal valorInicial;

    private BigDecimal valorFinal;

    private PotenciaVeiculo potencia;

    private TipoCombustivel combustivel;

    private String uf;

    private Cidade cidadePreferencia;

    private ModeloVeiculo modelo;

    private Fabricante fabricante;

    private TipoVeiculo tipoVeiculo;

    private List<Cor> cores;

    private List<Acessorio> acessorios;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public ModeloVeiculo getModelo() {
        return modelo;
    }

    public BigDecimal getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(BigDecimal valorInicial) {
        this.valorInicial = valorInicial;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public PotenciaVeiculo getPotencia() {
        return potencia;
    }

    public void setPotencia(PotenciaVeiculo potencia) {
        this.potencia = potencia;
    }

    public TipoCombustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(TipoCombustivel combustivel) {
        this.combustivel = combustivel;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Cidade getCidadePreferencia() {
        return cidadePreferencia;
    }

    public void setCidadePreferencia(Cidade cidadePreferencia) {
        this.cidadePreferencia = cidadePreferencia;
    }

    public void setModelo(ModeloVeiculo modelo) {
        this.modelo = modelo;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public List<Cor> getCores() {
        return cores;
    }

    public void setCores(List<Cor> cores) {
        this.cores = cores;
    }

    public List<Acessorio> getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(List<Acessorio> acessorios) {
        this.acessorios = acessorios;
    }

}