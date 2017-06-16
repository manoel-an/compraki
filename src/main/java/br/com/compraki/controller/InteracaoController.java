package br.com.compraki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Interacao;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.repository.Carros;
import br.com.compraki.repository.IntencaoCompras;
import br.com.compraki.security.UsuarioSistema;

@Controller
@RequestMapping("/interacao")
public class InteracaoController {

	private static final String ITR_VIEW = "interacao/fornecedor/PropostaFornecedor";

	@Autowired
	private Carros carros;

	@Autowired
	private IntencaoCompras intencoes;

	@GetMapping("/novo")
	public ModelAndView novo(@AuthenticationPrincipal User user, Interacao propostaFonecedor) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		ModelAndView mv = getDefaultObjectsModelAndViewDeProposta(usuarioSistema.getUsuario(), propostaFonecedor);
		return mv;
	}

	// renderiza na view os objetos da Porposta do fornecedor
	private ModelAndView getDefaultObjectsModelAndViewDeProposta(Usuario usuario, Interacao propostaFornecedor) {
		ModelAndView modelAndView = new ModelAndView(ITR_VIEW);

		modelAndView.addObject("veiculos", carros.findByUsuarioAndFetchEager(usuario));

		// POR ENQUANTO TÁ PUXANDO A INTENÇÃO ESTATICAMENTE PARA APRESENTAR NO
		// PropostaFornecedor.html
		// modelAndView.addObject("intencoesPropostas",intencoes.findOne(1L));
		modelAndView.addObject("tipos", TipoVeiculo.values());
		Carro carro = new Carro();
		carro.setUsuario(usuario);
		modelAndView.addObject("carro", carro);
		modelAndView.addObject("cadastroVeiculo", Boolean.FALSE);
		return modelAndView;
	}

}// fim
