package br.com.compraki.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import br.com.compraki.enuns.StatusInteracao;
import br.com.compraki.model.veiculo.Carro;

@Entity
@Table(name = "interacao")
public class Interacao implements Serializable {

    private static final long serialVersionUID = -8354269525264410932L;

    private Long codigo;
    private Carro veiculo;
    private Usuario fornecedor;
    private IntencaoCompra intencaoCompra;
    private String descricao;
    private BigDecimal valor;
    private Date dataCriacao;
    private Date dataModificacao;
    private StatusInteracao status;
    private Long codigoPessoa;
    private Long codigoUsuario;

    @PrePersist
    @PreUpdate
    public void configuraDatasCriacaoAlteracao() {
        this.dataModificacao = new Date();

        if (this.dataCriacao == null) {
            this.dataCriacao = new Date();
        }
    }

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    // @NotNull(message = "Ops! É necessário acrescentar o carro")
    @OneToOne
    @JoinColumn(name = "codigo_carro")
    public Carro getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Carro veiculo) {
        this.veiculo = veiculo;
    }

    @ManyToOne
    @JoinColumn(name = "codigo_fornecedor")
    // @NotNull(message = "Fornecedor não foi inserido")
    public Usuario getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Usuario fornecedor) {
        this.fornecedor = fornecedor;
    }

    @ManyToOne
    @JoinColumn(name = "codigo_intencao")
    // @NotNull(message = "Intencao de Compra não foi inserida")
    public IntencaoCompra getIntencaoCompra() {
        return intencaoCompra;
    }

    public void setIntencaoCompra(IntencaoCompra intencaoCompra) {
        this.intencaoCompra = intencaoCompra;
    }

    @NotBlank(message = "Ops! Valor é obrigatório")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // @NotNull(message = "Ops! insira o valor")
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao")
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_modificacao")
    public Date getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    @Enumerated(EnumType.STRING)
    public StatusInteracao getStatus() {
        return status;
    }

    public void setStatus(StatusInteracao status) {
        this.status = status;
    }

    @Transient
    public Long getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    @Transient
    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

}
