package br.com.compraki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Interacao;
import br.com.compraki.model.Pessoa;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.repository.Carros;
import br.com.compraki.repository.Pessoas;

@Service
public class InteracaoService {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private Pessoas pessoas;

	@Autowired
	private Carros carros;

	private static final String IT_DETALHES_VIEW = "intencaoCompra/DetalhesIntencaoCompra";

	private static final String ITR_VIEW = "interacao/fornecedor/PropostaFornecedor";

	public ModelAndView getDefaultModelAndView(Usuario usuario, IntencaoCompra intencaoCompra,
			Interacao propostaFornecedor) {
		ModelAndView modelAndView = new ModelAndView(IT_DETALHES_VIEW);
		ModelAndView mvProposta = new ModelAndView(ITR_VIEW);
		modelAndView.addObject("veiculos", carros.findByUsuarioAndFetchEager(usuario));
		modelAndView.addObject(mvProposta);
		propostaFornecedor.setIntencaoCompra(intencaoCompra);
		modelAndView.addObject("tipos", TipoVeiculo.values());
		Carro carro = new Carro();
		carro.setUsuario(usuario);
		modelAndView.addObject("carro", carro);
		modelAndView.addObject("cadastroVeiculo", Boolean.FALSE);
		modelAndView.addObject("propostaFonecedor", propostaFornecedor);
		modelAndView.addObject("intencaoCompra", intencaoCompra);
		Pessoa pessoa = this.pessoas.findByUsuario(usuario).isPresent() ? this.pessoas.findByUsuario(usuario).get()
				: null;
		modelAndView.addObject("telefone", pessoa != null ? pessoa.getTelefone().getNumeroUm().replace(" ", "") : "");
		modelAndView.addObject("codigoPessoa", pessoa != null ? pessoa.getCodigo() : 0);
		return modelAndView;
	}

	public PessoaService getPessoaService() {
		return pessoaService;
	}

}