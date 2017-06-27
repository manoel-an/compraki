package br.com.compraki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.service.IntencaoService;

@Controller
public class HomeController {

    @Autowired
    private IntencaoService intencaoService;

    @GetMapping("/")
    public ModelAndView dashboard(@AuthenticationPrincipal User user) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) user;
        ModelAndView mv = new ModelAndView("Home");
        mv.addObject("intencoes", this.intencaoService.getIntencaoCompras().porModeloOuCidade("",
                usuarioSistema.getUsuario().getCodigo()));
        return mv;
    }
}