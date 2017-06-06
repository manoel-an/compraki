package br.com.compraki.model.veiculo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import br.com.compraki.enuns.TipoVeiculo;

@Entity
@Table(name = "fabricante")
public class Fabricante {

    private Long codigo;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private TipoVeiculo tipoVeiculo;

    public Fabricante() {

    }

    public Fabricante(Long codigo, String nome, TipoVeiculo tipoVeiculo) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipoVeiculo = tipoVeiculo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCodigo() {
        return codigo;

    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    ////
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Enumerated(EnumType.STRING)
    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
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
        Fabricante other = (Fabricante) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }

}
