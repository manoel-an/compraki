package br.com.compraki.repository.filter;

import java.util.List;

import org.springframework.format.annotation.NumberFormat;

import com.ibm.icu.math.BigDecimal;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Cor;
import br.com.compraki.model.carro.Acessorio;
import br.com.compraki.model.carro.Fabricante;
import br.com.compraki.model.carro.ModeloCarro;

public class IntencaoFilter {

	private Long codigo;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorInicial;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorFinal;

	private ModeloCarro modelo;

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

	public ModeloCarro getModelo() {
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

	public void setModelo(ModeloCarro modelo) {
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