package br.com.compraki.model.carro;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "carro")
@NamedQueries({@NamedQuery(name = "Carro.buscarTodos", query = "select c from Carro c") ,
        @NamedQuery(name = "Carro.buscarCarroComAcessorios", query = "select c "
                + "	from Carro c JOIN c.acessorios a " + " where c.codigo = :codigo") ,
        @NamedQuery(name = "Carro.buscarCarroSemAcessorios", query = "select c " + "	from Carro c "
                + " where c.codigo = :codigo")

})
public class Carro {

    private Long codigo;
    private String cor;
    private Boolean ipvaPago;
    private String descricao;
    private ModeloCarro modelo;
    private List<Acessorio> acessorios;
    private String foto;
    @Column(name = "content_type")
    private String contentType;
    private String url;
    private Date dataCriacao;
    private Date dataModificacao;

    // getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Column(name = "ipva_pago")
    public Boolean getIpvaPago() {
        return ipvaPago;
    }

    public void setIpvaPago(Boolean ipvaPago) {
        this.ipvaPago = ipvaPago;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "codigo_modelo")
    public ModeloCarro getModelo() {
        return modelo;
    }

    public void setModelo(ModeloCarro modelo) {
        this.modelo = modelo;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "carro_acessorio", joinColumns = @JoinColumn(name = "codigo_carro") , inverseJoinColumns = @JoinColumn(name = "codigo_acessorio") )
    public List<Acessorio> getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(List<Acessorio> acessorios) {
        this.acessorios = acessorios;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        Carro other = (Carro) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }

}
