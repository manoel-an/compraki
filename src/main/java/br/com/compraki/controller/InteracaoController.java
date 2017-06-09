package br.com.compraki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.model.Interacao;

@Controller
@RequestMapping("/interacao")
public class InteracaoController {

    private static final String ITR_VIEW = "interacao/PropostaFornecedor";

    @GetMapping("/novo")
    public ModelAndView novo(Interacao propostaFonecedor) {
        ModelAndView mv = getDefaultObjectsModelAndViewDeProposta(propostaFonecedor);
        return mv;
    }

   
    //renderiza na view os objetos da Porposta do fornecedor
    private ModelAndView getDefaultObjectsModelAndViewDeProposta(Interacao propostaFornecedor) {
        ModelAndView modelAndView = new ModelAndView(ITR_VIEW);
        
        return modelAndView;
    }

}// fim
