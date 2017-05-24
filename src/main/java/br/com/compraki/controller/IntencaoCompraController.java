package br.com.compraki.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.compraki.enuns.TipoCombustivel;
import br.com.compraki.enuns.UF;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.carro.ModeloCarro;
import br.com.compraki.repository.Fabricantes;
import br.com.compraki.repository.IntencaoCompras;
import br.com.compraki.repository.ModelosCarros;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.validator.IntencaoValidator;

@Controller
@RequestMapping("/intencoes")
public class IntencaoCompraController {

	private static final String IT_VIEW = "intencaoCompra/IntencaoCompra";

	@Autowired
	private IntencaoCompras intencaoCompras;

	@Autowired
	private Fabricantes fabricantes;

	@Autowired
	private ModelosCarros modelosCarros;

	@Autowired
	private IntencaoValidator validator;

	@GetMapping("/novo")
	public ModelAndView novo(@AuthenticationPrincipal User user, IntencaoCompra intencaoCompra) {
		ModelAndView mv = getDefaultObjectsModelAndView(intencaoCompra, user);
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@AuthenticationPrincipal User user, @Valid IntencaoCompra intencaoCompra,
			BindingResult result, RedirectAttributes attributes) {
		validator.validate(intencaoCompra, result);
		if (result.hasErrors()) {
			return novo(user, intencaoCompra);
		}
		return null;
	}

	@RequestMapping(value = "buscarModelos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ModeloCarro> pesquisarPorCodigoEstado(
			@RequestParam(name = "marca", defaultValue = "-1") Long codigoMarca) {
		return this.modelosCarros.findByFabricanteCodigo(codigoMarca);
	}

	private ModelAndView getDefaultObjectsModelAndView(IntencaoCompra intencaoCompra, User user) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		System.out.println(usuarioSistema.getUsuario().getCodigo());
		ModelAndView modelAndView = new ModelAndView(IT_VIEW);
		modelAndView.addObject("fabricantes", this.fabricantes.findAll());
		modelAndView.addObject("tiposCombustivel", TipoCombustivel.values());
		return modelAndView;
	}

}// fim
