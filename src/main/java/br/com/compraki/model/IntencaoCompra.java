package br.com.compraki.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.compraki.enuns.PotenciaVeiculo;
import br.com.compraki.enuns.StatusAtivoInativo;
import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.helper.IntencaoHelper;
import br.com.compraki.model.veiculo.Acessorio;
import br.com.compraki.model.veiculo.ModeloVeiculo;

@Entity
@Table(name = "intencao_de_compra")
public class IntencaoCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Ops! Escolha o ano do veículo desejado")
	private String ano;

	@NotNull(message = "Ops! Valor é obrigatório")
	private BigDecimal valor;

	// Escolher entre por exemplo alcool, gasolina, flex
	@NotBlank(message = "Ops! Escolha qual combustível para o veículo desejado")
	@Column(name = "tipo_combustivel")
	private String tipoCombustivel;

	@NotBlank(message = "Ops! As características são importantes para uma intenção de compra")
	@Column(name = "outras_caracteristicas")
	private String outrasCaracteristicas;

	@NotNull(message = "Ops! Potência do veículo também ajuda")
	@Enumerated(EnumType.STRING)
	private PotenciaVeiculo potencia;

	@NotBlank(message = "Ops! A descrição é muito importante")
	private String descricao;

	@NotBlank(message = "Ops! Ajuda muito se você colocar se sua roda é comum ou uma mais específica. Se não sabe coloque qualquer")
	@Column(name = "tipo_de_roda")
	private String tipoDeRoda;

	@Column(name = "aro_roda")
	private String aroRoda;

	@NotBlank(message = "Ops! É importante colocar o Estado de preferência")
	@Column(name = "uf_preferencia")
	private String uf;

	@Column(name = "cidade_preferencia")
	private String cidadePreferencia;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "codigo_modelo")
	private ModeloVeiculo modelo;

	// @NotBlank(message="Ops! Esqueceu o tipo de veculo")
	@Column(name = "tipo_de_veiculo")
	@Enumerated(EnumType.STRING)
	private TipoVeiculo tipoVeiculo;

	@Column(name = "status_de_intencao")
	@Enumerated(EnumType.STRING)
	private StatusAtivoInativo statusIntencao;

	@Transient
	private IntencaoHelper intencaoHelper;

	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "intencao_acessorios_desejados", joinColumns = @JoinColumn(name = "codigo_intencao"), inverseJoinColumns = @JoinColumn(name = "codigo_acessorio"))
	private List<Acessorio> acessorios;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "intencao_cores_desejadas", joinColumns = @JoinColumn(name = "codigo_intencao"), inverseJoinColumns = @JoinColumn(name = "codigo_cor"))
	private List<Cor> cores;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_modificacao")
	private Date dataModificacao;

	@Transient
	private String cidade;

	@PrePersist
	@PreUpdate
	public void configuraDatasCriacaoAlteracao() {
		this.dataModificacao = new Date();

		if (this.dataCriacao == null) {
			this.dataCriacao = new Date();
		}
	}

	// Getters and setters
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getTipoCombustivel() {
		return tipoCombustivel;
	}

	public void setTipoCombustivel(String tipoCombustivel) {
		this.tipoCombustivel = tipoCombustivel;
	}

	public String getOutrasCaracteristicas() {
		return outrasCaracteristicas;
	}

	public void setOutrasCaracteristicas(String outrasCaracteristicas) {
		this.outrasCaracteristicas = outrasCaracteristicas;
	}

		public PotenciaVeiculo getPotencia() {
		return potencia;
	}

	public void setPotencia(PotenciaVeiculo potencia) {
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

	public String getAroRoda() {
		return aroRoda;
	}

	public void setAroRoda(String aroRoda) {
		this.aroRoda = aroRoda;
	}

	public String getCidadePreferencia() {
		return cidadePreferencia;
	}

	public void setCidadePreferencia(String cidadePreferencia) {
		this.cidadePreferencia = cidadePreferencia;
	}

	public ModeloVeiculo getModelo() {
		return modelo;
	}

	public void setModelo(ModeloVeiculo modelo) {
		this.modelo = modelo;
	}

	public TipoVeiculo getTipoVeiculo() {
		if (tipoVeiculo == null) {
			tipoVeiculo = TipoVeiculo.CARRO;
		}
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public StatusAtivoInativo getStatusIntencao() {
		return statusIntencao;
	}

	public void setStatusIntencao(StatusAtivoInativo statusIntencao) {
		this.statusIntencao = statusIntencao;
	}

	public IntencaoHelper getIntencaoHelper() {
		if (intencaoHelper == null) {
			intencaoHelper = new IntencaoHelper();
		}
		return intencaoHelper;
	}

	public void setIntencaoHelper(IntencaoHelper intencaoHelper) {
		this.intencaoHelper = intencaoHelper;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(List<Acessorio> acessorios) {
		this.acessorios = acessorios;
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

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Cor> getCores() {
		return cores;
	}

	public void setCores(List<Cor> cores) {
		this.cores = cores;
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
