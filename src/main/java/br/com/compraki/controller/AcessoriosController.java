package br.com.compraki.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.model.carro.Carro;
import br.com.compraki.repository.Acessorios;
import br.com.compraki.repository.filter.AcessorioFilter;
import br.com.compraki.repository.paginacao.PageWrapper;

@Controller
@RequestMapping("/acessorios")
public class AcessoriosController {

    @Autowired
    private Acessorios acessorios;

    @GetMapping
    public ModelAndView pesquisar(AcessorioFilter acessorioFilter, BindingResult result,
            @PageableDefault(size = 7) Pageable pageable, HttpServletRequest httpServletRequest) {
        ModelAndView mv = new ModelAndView("acessorio/PesquisaAcessorios");
        PageWrapper<Carro> paginaWrapper = new PageWrapper<>(this.acessorios.filtrar(acessorioFilter, pageable),
                httpServletRequest);
        mv.addObject("pagina", paginaWrapper);
        return mv;
    }

}
