package br.com.compraki.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import br.com.compraki.model.carro.Acessorio;
import br.com.compraki.model.carro.ModeloCarro;

@Entity
@Table(name="intencao_de_compra")
public class IntencaoCompra {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	private String marca;
	
	private String ano;
	
	private String cor;
	
	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "3.000.00", message = "Valor não pode ser menor que R$ 3.000,00")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	
	//Escolher entre por exemplo alcool, gasolina, flex
	@Column(name="tipo_combustivel")
	private String tipoCombustivel;
	
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
	
	public String getTipoCombustivel() {
		return tipoCombustivel;
	}
	public void setTipoCombustivel(String tipoCombustivel) {
		this.tipoCombustivel = tipoCombustivel;
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
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	@PrePersist
	@PreUpdate
	public void configuraDatasCriacaoAlteracao() {
		this.dataModificacao = new Date();
		
		if (this.dataCriacao == null) {
			this.dataCriacao = new Date();
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntencaoCompra other = (IntencaoCompra) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
		
}
