package br.com.compraki.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Cor;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.carro.Acessorio;
import br.com.compraki.model.carro.ModeloCarro;
import br.com.compraki.repository.Acessorios;
import br.com.compraki.repository.Cores;
import br.com.compraki.repository.Fabricantes;
import br.com.compraki.repository.IntencaoCompras;
import br.com.compraki.repository.ModelosCarros;

@Service
public class IntencaoService {

	@Autowired
	private IntencaoCompras intencaoCompras;
	@Autowired
	private ModelosCarros modelos;
	@Autowired
	private Cores cores;
	@Autowired
	private Fabricantes fabricantes;
	@Autowired
	private Acessorios acessorios;

	public ModelAndView getModelAndViewTipoVeiculo(Long codigoIntencao, TipoVeiculo tipoVeiculo, Long[] acessorios,
			Long[] cores) {
		ModelAndView modelAndView = null;
		if (tipoVeiculo.equals(TipoVeiculo.CARRO)) {
			modelAndView = new ModelAndView("intencaoCompra/fragments/TipoCarro");
		} else if (tipoVeiculo.equals(TipoVeiculo.MOTO)) {
			modelAndView = new ModelAndView("intencaoCompra/fragments/TipoMoto");
		} else {
			modelAndView = new ModelAndView("intencaoCompra/fragments/TipoPesado");
		}
		IntencaoCompra intencaoCompra = null;
		intencaoCompra = codigoIntencao != null ? this.intencaoCompras.getIntencaoCompraCompleto(codigoIntencao)
				: new IntencaoCompra();
		intencaoCompra.setTipoVeiculo(tipoVeiculo);
		modelAndView.addObject("intencaoCompra", intencaoCompra);
		modelAndView.addObject("fabricantes", this.fabricantes.findByTipoVeiculo(tipoVeiculo));
		if (acessorios != null) {
			intencaoCompra.setAcessorios(this.acessorios.findByCodigoIn(acessorios));
		}
		if (cores != null) {
			intencaoCompra.setCores(this.cores.findByCodigoIn(cores));
		}
		modelAndView.addObject("acessorios", getSelectedAcessorrios(intencaoCompra));
		modelAndView.addObject("cores", getSelectedCores(intencaoCompra));
		return modelAndView;
	}

	@Transactional
	public void salvar(IntencaoCompra intencaoCompra, Usuario usuario) throws NegocioException {
		try {
			setObjects(intencaoCompra, usuario);
			if (intencaoCompra != null && intencaoCompra.getUsuario() != null) {
				this.intencaoCompras.save(intencaoCompra);
			}
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}

	}

	public void setObjects(IntencaoCompra intencaoCompra, Usuario usuario) {
		// seta Usuario
		// Não precisa deste trecho aqui Claudio
		// Usuario usuarioFind = usuarios.findByCodigo(usuario.getCodigo());
		// Pode atribuir o usuario diretamente; pois o spring security ja
		// disponibiliza o mesmo a qualquer tempo
		intencaoCompra.setUsuario(usuario);

		// busca e setaModelo
		ModeloCarro modelo = this.modelos.findByCodigo(intencaoCompra.getModelo().getCodigo());
		intencaoCompra.setModelo(modelo);

		// seta data
		intencaoCompra.setDataCriacao(new Date());
		// seta Cores
		intencaoCompra.setCores(getCoresGerenciadas(intencaoCompra));
	}

	public List<Acessorio> getSelectedAcessorrios(IntencaoCompra intencaoCompra) {
		List<Acessorio> acessorios = this.acessorios.findByTipoVeiculo(intencaoCompra.getTipoVeiculo());
		int count = 0;
		int itensSelecionados = intencaoCompra.getAcessorios() != null ? intencaoCompra.getAcessorios().size() : 0;
		boolean tipodiferente = false;
		if (intencaoCompra.getAcessorios() != null && intencaoCompra.getAcessorios().size() > 0) {
			for (Acessorio acessorio : intencaoCompra.getAcessorios()) {
				if (!acessorio.getTipoVeiculo().equals(intencaoCompra.getTipoVeiculo())) {
					tipodiferente = true;
					break;
				}
			}
		}
		if (itensSelecionados > 0 && !tipodiferente) {
			do {
				Acessorio acessorio = intencaoCompra.getAcessorios().get(count);
				Long codigo = acessorio.getCodigo();
				int pos = acessorio.getPosicao();
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

	public List<Cor> getSelectedCores(IntencaoCompra intencaoCompra) {
		List<Cor> cores = this.cores.findAll();
		int count = 0;
		int itensSelecionados = intencaoCompra.getCores() != null ? intencaoCompra.getCores().size() : 0;
		if (itensSelecionados > 0) {
			do {
				Cor cor = intencaoCompra.getCores().get(count);
				Long codigo = cor.getCodigo();
				int pos = cor.getCodigo().intValue();
				String descricao = cor.getDescricao();
				cores.set(pos - 1, new Cor(codigo, descricao, Boolean.TRUE));
				count++;
				if (count == itensSelecionados) {
					break;
				}
			} while (count <= (itensSelecionados - 1));

		}
		return cores;
	}

	private List<Cor> getCoresGerenciadas(IntencaoCompra intencaoCompra) {
		Long[] codigos = new Long[intencaoCompra.getCores().size()];
		int count = 0;
		for (Cor cor : intencaoCompra.getCores()) {
			codigos[count] = cor.getCodigo();
			count++;
		}
		return this.cores.findByCodigoIn(codigos);
	}

	public Cores getCores() {
		return cores;
	}

}// fim