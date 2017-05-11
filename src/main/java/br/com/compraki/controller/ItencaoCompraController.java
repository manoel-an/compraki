package br.com.compraki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.repository.IntencaoCompras;

@Controller
@RequestMapping("/intencao")
public class ItencaoCompraController {
	
	private static final String IT_VIEW = "IntencaoCompra";
	
	@Autowired
	private IntencaoCompras intencaoCompras;

	
	@GetMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(IT_VIEW);
		mv.addObject(new IntencaoCompra());
		return mv;
	}

	//FAZER MÉTODO SALVAR
	
}
