package br.com.compraki.model.carro;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.compraki.enuns.CategoriaCarro;
import br.com.compraki.enuns.CategoriaMoto;
import br.com.compraki.enuns.CategoriaPesado;

@Entity
@Table(name = "modelo_carro")
public class ModeloCarro {

	private Long codigo;
	private String descricao;
	private Fabricante fabricante;
	private String categoria;
	private CategoriaCarro categoriaCarro;
	private CategoriaMoto categoriaMoto;
	private CategoriaPesado categoriaPesado;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@ManyToOne
	@JoinColumn(name = "codigo_fabricante")
	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public String getCategoria() {
		if (getCategoriaCarro() != null || getCategoriaMoto() != null || getCategoriaPesado() != null) {
			categoria = getCategoriaCarro() != null ? getCategoriaCarro().toString()
					: (getCategoriaMoto() != null ? getCategoriaMoto().toString() : getCategoriaPesado().toString());
		}
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Transient
	public CategoriaCarro getCategoriaCarro() {
		if (categoria != null) {
			categoriaCarro = CategoriaCarro.retornaSituacao(categoria);
		}
		return categoriaCarro;
	}

	public void setCategoriaCarro(CategoriaCarro categoriaCarro) {
		this.categoriaCarro = categoriaCarro;
	}

	@Transient
	public CategoriaMoto getCategoriaMoto() {
		if (categoria != null) {
			categoriaMoto = CategoriaMoto.retornaSituacao(categoria);
		}
		return categoriaMoto;
	}

	public void setCategoriaMoto(CategoriaMoto categoriaMoto) {
		this.categoriaMoto = categoriaMoto;
	}

	@Transient
	public CategoriaPesado getCategoriaPesado() {
		if (categoria != null) {
			categoriaPesado = CategoriaPesado.retornaSituacao(categoria);
		}
		return categoriaPesado;
	}

	public void setCategoriaPesado(CategoriaPesado categoriaPesado) {
		this.categoriaPesado = categoriaPesado;
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
		ModeloCarro other = (ModeloCarro) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
