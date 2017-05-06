package br.com.compraki.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.model.Pessoa;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@GetMapping("/novo")
	public ModelAndView login(@AuthenticationPrincipal User user, Pessoa pessoa) {
		ModelAndView modelAndView = getDefaultObjectsModelAndView(user, pessoa);
		return modelAndView;
	}

	private ModelAndView getDefaultObjectsModelAndView(User user, Pessoa pessoa) {
		ModelAndView modelAndView = new ModelAndView("usuario/CadastroUsuario");
		return modelAndView;
	}
}
