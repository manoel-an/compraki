package br.com.compraki.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Cor;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Acessorio;
import br.com.compraki.model.veiculo.ModeloVeiculo;
import br.com.compraki.repository.Acessorios;
import br.com.compraki.repository.Cores;
import br.com.compraki.repository.Fabricantes;
import br.com.compraki.repository.IntencaoCompras;
import br.com.compraki.repository.ModelosVeiculos;

@Service
public class IntencaoService {

	@Autowired
	private IntencaoCompras intencaoCompras;
	@Autowired
	private ModelosVeiculos modelos;
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

	public void getFieldError(IntencaoCompra intencaoCompra, BindingResult result) {
		if (result.getFieldError("modelo.fabricante") != null) {
			intencaoCompra.getIntencaoHelper().setErroMarca(Boolean.TRUE);
		} else {
			intencaoCompra.getIntencaoHelper().setErroMarca(Boolean.FALSE);
			intencaoCompra.getIntencaoHelper().setMarca(intencaoCompra.getModelo().getFabricante().getCodigo());
		}
		if (result.getFieldError("modelo") != null) {
			intencaoCompra.getIntencaoHelper().setErroModelo(Boolean.TRUE);
		} else {
			intencaoCompra.getIntencaoHelper().setErroModelo(Boolean.FALSE);
			intencaoCompra.getIntencaoHelper().setModelo(intencaoCompra.getModelo().getCodigo());
		}

		if (result.getFieldError("cores") != null) {
			intencaoCompra.getIntencaoHelper().setErroCores(Boolean.TRUE);
		} else {
			intencaoCompra.getIntencaoHelper().setErroCores(Boolean.FALSE);
			intencaoCompra.getIntencaoHelper()
					.setCores(intencaoCompra.getIntencaoHelper().getCoresJSON(intencaoCompra.getCores()));
		}
		if (result.getFieldError("acessorios") != null) {
			intencaoCompra.getIntencaoHelper().setErroAcessorios(Boolean.TRUE);
		} else {
			intencaoCompra.getIntencaoHelper().setErroAcessorios(Boolean.FALSE);
			intencaoCompra.getIntencaoHelper().setAcessorios(
					intencaoCompra.getIntencaoHelper().getAcessoriosJSON(intencaoCompra.getAcessorios()));
		}
		intencaoCompra.getIntencaoHelper().setTipoVeiculo(intencaoCompra.getTipoVeiculo());
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
		
		intencaoCompra.setUsuario(usuario);

		// busca e setaModelo
		ModeloVeiculo modelo = this.modelos.findByCodigo(intencaoCompra.getModelo().getCodigo());
		intencaoCompra.setModelo(modelo);

		// seta data
		intencaoCompra.setDataCriacao(new Date());
		// seta Cores
		intencaoCompra.setCores(getCoresGerenciadas(intencaoCompra));
		intencaoCompra.setAcessorios(getAcessoriosGerenciados(intencaoCompra));
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

	private List<Acessorio> getAcessoriosGerenciados(IntencaoCompra intencaoCompra) {
		Long[] codigos = new Long[intencaoCompra.getAcessorios().size()];
		int count = 0;
		for (Acessorio acessorio : intencaoCompra.getAcessorios()) {
			codigos[count] = acessorio.getCodigo();
			count++;
		}
		return this.acessorios.findByCodigoIn(codigos);
	}

	public Cores getCores() {
		return cores;
	}

	public IntencaoCompras getIntencaoCompras() {
		return intencaoCompras;
	}

}// fim