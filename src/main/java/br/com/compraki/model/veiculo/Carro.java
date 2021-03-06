package br.com.compraki.model.veiculo;

import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Cor;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.helper.CarroHelper;

@Entity
@Table(name = "carro")
public class Carro {

    private Long codigo;
    private Cor cor;
    private Boolean ipvaPago;
    private String descricao;
    private ModeloVeiculo modelo;
    private List<Acessorio> acessorios;
    private String foto;
    @Column(name = "content_type")
    private String contentType;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private boolean novaFoto;
    private Usuario usuario;
    private TipoVeiculo tipoVeiculo;
    private CarroHelper carroHelper;

    // getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @OneToOne
    @JoinColumn(name = "codigo_cor")
    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
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
    public ModeloVeiculo getModelo() {
        return modelo;
    }

    public void setModelo(ModeloVeiculo modelo) {
        this.modelo = modelo;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @Column(name = "data_criacao")
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Column(name = "data_modificacao")
    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    @PrePersist
    @PreUpdate
    public void configuraDatasCriacaoAlteracao() {
        this.dataModificacao = LocalDateTime.now();

        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDateTime.now();
        }
    }

    @Transient
    public boolean isNovaFoto() {
        return novaFoto;
    }

    public void setNovaFoto(boolean novaFoto) {
        this.novaFoto = novaFoto;
    }

    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Enumerated(EnumType.STRING)
    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    @Transient
    public CarroHelper getCarroHelper() {
        if (carroHelper == null) {
            carroHelper = new CarroHelper();
        }
        return carroHelper;
    }

    public void setCarroHelper(CarroHelper carroHelper) {
        this.carroHelper = carroHelper;
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
