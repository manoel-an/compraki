package br.com.compraki.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.enuns.EnumSexo;
import br.com.compraki.model.Pessoa;
import br.com.compraki.security.UsuarioSistema;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@GetMapping("/novo")
	public ModelAndView login(@AuthenticationPrincipal User user, Pessoa pessoa) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		ModelAndView modelAndView = getDefaultObjectsModelAndView(pessoa, usuarioSistema);
		return modelAndView;
	}

	private ModelAndView getDefaultObjectsModelAndView(Pessoa pessoa, UsuarioSistema usuarioSistema) {
		ModelAndView modelAndView = new ModelAndView("usuario/CadastroUsuario");
		modelAndView.addObject("sexos", EnumSexo.values());
		return modelAndView;
	}
}
