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

import br.com.compraki.enuns.EnumSexo;
import br.com.compraki.enuns.TipoPessoa;
import br.com.compraki.enuns.UF;
import br.com.compraki.model.Pessoa;
import br.com.compraki.repository.Grupos;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.validator.PessoaValidator;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private Grupos grupos;

	@Autowired
	private PessoaValidator validator;

	@GetMapping("/novo")
	public ModelAndView novo(@AuthenticationPrincipal User user, Pessoa pessoa) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		ModelAndView modelAndView = getDefaultObjectsModelAndView(pessoa, usuarioSistema);
		return modelAndView;
	}

	@PostMapping("/novo")
	public ModelAndView salvarCadastro(@AuthenticationPrincipal User user, @Valid Pessoa pessoa, BindingResult result,
			RedirectAttributes attributes) {
		validator.validate(pessoa, result);
		if (result.hasErrors()) {
			return novo(user, pessoa);
		}
		return null;

	}

	private ModelAndView getDefaultObjectsModelAndView(Pessoa pessoa, UsuarioSistema usuarioSistema) {
		ModelAndView modelAndView = new ModelAndView("usuario/CadastroUsuario");
		pessoa.setUsuario(usuarioSistema.getUsuario());
		modelAndView.addObject("sexos", EnumSexo.values());
		modelAndView.addObject("tipos", TipoPessoa.values());
		modelAndView.addObject("estados", UF.values());
		modelAndView.addObject("grupos", grupos.findAll());
		modelAndView.addObject("pessoa", pessoa);
		return modelAndView;
	}
}
