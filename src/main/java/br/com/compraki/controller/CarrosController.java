package br.com.compraki.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.compraki.enuns.Categoria;
import br.com.compraki.model.carro.Carro;
import br.com.compraki.service.CarroService;
import br.com.compraki.validator.CarroValidator;

@Controller
@RequestMapping("/carros")
public class CarrosController {

    @Autowired
    private CarroService carroService;

    @Autowired
    private CarroValidator validator;

    @GetMapping("/novo")
    public ModelAndView novo(Carro carro) {
        ModelAndView modelAndView = getDefaultObjectsModelAndView(carro);
        return modelAndView;
    }

    @PostMapping("/novo")
    public ModelAndView salvarPessoa(@Valid Carro carro, BindingResult result, RedirectAttributes attributes) {
        validator.validate(carro, result);
        if (result.hasErrors()) {
            return this.novo(carro);
        }
        return this.novo(carro);
    }

    private ModelAndView getDefaultObjectsModelAndView(Carro carro) {
        ModelAndView modelAndView = new ModelAndView("carro/CadastroCarro");
        modelAndView.addObject("fabricantes", this.carroService.getFabricantes().findAll());
        modelAndView.addObject("categorias", Categoria.values());
        modelAndView.addObject("acessorios", this.carroService.getSelectedAcessorrios(carro));
        return modelAndView;
    }
}
