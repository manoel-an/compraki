package br.com.compraki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Interacao;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.repository.Carros;
import br.com.compraki.security.UsuarioSistema;



@Controller
@RequestMapping("/detalhesIntencao")
public class DetalhesIntencaoCompraController {

    private static final String IT_DETALHES_VIEW = "intencaoCompra/DetalhesIntencaoCompra";
	private static final String ITR_VIEW = "interacao/fornecedor/PropostaFornecedor";
    
    @Autowired
	private Carros carros;
          
    /*@GetMapping("/novo")
	public ModelAndView detalhe() {
    	ModelAndView modelAndView = new ModelAndView(IT_DETALHES_VIEW);
		return modelAndView;
	}*/

    @GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") IntencaoCompra intencaoCompra,
			@AuthenticationPrincipal User user, Interacao propostaFonecedor) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		ModelAndView mv = getDefaultObjectsModelAndViewDeProposta(usuarioSistema.getUsuario(), intencaoCompra,
				propostaFonecedor);
		return mv;
	}

	// renderiza na view os objetos da Porposta do fornecedor
	private ModelAndView getDefaultObjectsModelAndViewDeProposta(Usuario usuario, IntencaoCompra intencaoCompra,
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
		return modelAndView;
	}

   

  }// fim