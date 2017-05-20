package br.com.compraki.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.repository.IntencaoCompras;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.validator.IntencaoValidator;

@Controller
@RequestMapping("/intencoes")
public class IntencaoCompraController {

	private static final String IT_VIEW = "intencaoCompra/IntencaoCompra";

	@Autowired
	private IntencaoCompras intencaoCompras;

	@Autowired
	private IntencaoValidator validator;

	@GetMapping("/novo")
	public ModelAndView novo(@AuthenticationPrincipal User user, IntencaoCompra intencaoCompra) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		System.out.println(usuarioSistema.getUsuario().getCodigo());
		ModelAndView mv = new ModelAndView(IT_VIEW);
		// mv.addObject(new IntencaoCompra());
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

}// fim
