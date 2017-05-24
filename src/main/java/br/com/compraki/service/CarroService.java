package br.com.compraki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.compraki.model.carro.Acessorio;
import br.com.compraki.model.carro.Carro;
import br.com.compraki.model.carro.ModeloCarro;
import br.com.compraki.repository.Acessorios;
import br.com.compraki.repository.Carros;
import br.com.compraki.repository.Fabricantes;
import br.com.compraki.repository.ModelosCarros;

@Service
public class CarroService {

	@Autowired
	private Fabricantes fabricantes;

	@Autowired
	private Acessorios acessorios;

	@Autowired
	private Carros carros;

	@Autowired
	private ModelosCarros modelosCarros;

	public List<Acessorio> getSelectedAcessorrios(Carro carro) {
		List<Acessorio> acessorios = this.acessorios.findAll();
		int count = 0;
		int itensSelecionados = carro.getAcessorios() != null ? carro.getAcessorios().size() : 0;
		if (itensSelecionados > 0) {
			do {
				Acessorio acessorio = carro.getAcessorios().get(count);
				Long codigo = acessorio.getCodigo();
				int pos = acessorio.getCodigo().intValue();
				String descricao = acessorio.getDescricao();
				acessorios.set(pos - 1, new Acessorio(codigo, descricao, Boolean.TRUE));
				count++;
				if (count == itensSelecionados) {
					break;
				}
			} while (count <= (itensSelecionados - 1));

		}
		return acessorios;
	}

	@Transactional
	public void salvarCarro(Carro carro) throws NegocioException {
		try {
			ModeloCarro modelo = this.modelosCarros.saveAndFlush(carro.getModelo());
			carro.setModelo(modelo);
			carro.setAcessorios(getAcessoriosGerenciados(carro));
			this.carros.save(carro);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	private List<Acessorio> getAcessoriosGerenciados(Carro carro) {
		Long[] codigos = new Long[carro.getAcessorios().size()];
		int count = 0;
		for (Acessorio acessorio : carro.getAcessorios()) {
			codigos[count] = acessorio.getCodigo();
			count++;
		}
		return this.acessorios.findByCodigoIn(codigos);
	}

	public Fabricantes getFabricantes() {
		return fabricantes;
	}

	public Acessorios getAcessorios() {
		return acessorios;
	}

}