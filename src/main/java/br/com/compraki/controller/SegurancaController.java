package br.com.compraki.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SegurancaController {

	@GetMapping("/login")
	public ModelAndView login(@AuthenticationPrincipal User user) {
		ModelAndView modelAndView = getDefaultObjectsModelAndView(user);
		return modelAndView;
	}

	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}

	private ModelAndView getDefaultObjectsModelAndView(User user) {
		ModelAndView modelAndView = null;
		if (user != null) {
			modelAndView = new ModelAndView("redirect:/");
		} else {
			modelAndView = new ModelAndView("Login");
		}
		return modelAndView;
	}
}
