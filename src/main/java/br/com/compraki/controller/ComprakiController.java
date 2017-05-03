package br.com.compraki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ComprakiController {

	@RequestMapping("/info")
	public ModelAndView compraki() {
		ModelAndView mv = new ModelAndView("compraki/Compraki");
		return mv;
	}

}