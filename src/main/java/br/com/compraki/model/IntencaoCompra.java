package br.com.compraki.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.compraki.model.carro.Acessorio;
import br.com.compraki.model.carro.ModeloCarro;

@Table(name="intencao_de_compra")
public class IntencaoCompra {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	private String marca;
	
	private String ano;
	
	private String cor;
	
	//Escolher entre por exemplo alcool, gasolina, flex
	@Column(name="tipo_combustivel")
	private String tipoCombustível;
	
	@Column(name="outras_caracteristicas")
	private String outrasCaracterísticas;
	
	private String potencia;
	
	private String descricao;
	
	@Column(name="tipo_de_roda")
	private String tipoDeRoda;
	
	@Column(name="cor_parachoque")
	private String corParachoque;
	
	@Column(name="cidade_preferencia")
	private String cidadePreferencia;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="codigo_modelo")
	private ModeloCarro modelo;
	
	@Column(name="outro_modelo")
	private String outroModelo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="intencao_acessorios_desejados"
				, joinColumns=@JoinColumn(name="codigo_intencao")
				, inverseJoinColumns=@JoinColumn(name="codigo_acessorio"))
	private List<Acessorio> acessorios;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_criacao")
	private Date dataCriacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_modificacao")
	private Date dataModificacao;
	
	//Getters and setters
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public String getTipoCombustível() {
		return tipoCombustível;
	}
	public void setTipoCombustível(String tipoCombustível) {
		this.tipoCombustível = tipoCombustível;
	}
	
	public String getOutrasCaracterísticas() {
		return outrasCaracterísticas;
	}
	public void setOutrasCaracterísticas(String outrasCaracterísticas) {
		this.outrasCaracterísticas = outrasCaracterísticas;
	}
	
	public String getPotencia() {
		return potencia;
	}
	public void setPotencia(String potencia) {
		this.potencia = potencia;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoDeRoda() {
		return tipoDeRoda;
	}
	public void setTipoDeRoda(String tipoDeRoda) {
		this.tipoDeRoda = tipoDeRoda;
	}
	
	public String getCorParachoque() {
		return corParachoque;
	}
	public void setCorParachoque(String corParachoque) {
		this.corParachoque = corParachoque;
	}
	
	public String getCidadePreferencia() {
		return cidadePreferencia;
	}
	public void setCidadePreferencia(String cidadePreferencia) {
		this.cidadePreferencia = cidadePreferencia;
	}
	
	public ModeloCarro getModelo() {
		return modelo;
	}
	public void setModelo(ModeloCarro modelo) {
		this.modelo = modelo;
	}
	
	public String getOutroModelo() {
		return outroModelo;
	}
	public void setOutroModelo(String outroModelo) {
		this.outroModelo = outroModelo;
	}
	
	public List<Acessorio> getAcessorios() {
		return acessorios;
	}
	public void setAcessorios(List<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Date getDataModificacao() {
		return dataModificacao;
	}
	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	
	@PrePersist
	@PreUpdate
	public void configuraDatasCriacaoAlteracao() {
		this.dataModificacao = new Date();
		
		if (this.dataCriacao == null) {
			this.dataCriacao = new Date();
		}
	}
		
}
