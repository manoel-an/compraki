package br.com.compraki.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Interacao;
import br.com.compraki.model.Telefone;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.service.InteracaoService;
import br.com.compraki.service.NegocioException;

@Controller
@RequestMapping("/detalhesIntencao")
public class DetalhesIntencaoCompraController {

    @Autowired
    private InteracaoService interacaoService;

    @GetMapping("/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") IntencaoCompra intencaoCompra,
            @AuthenticationPrincipal User user) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) user;
        ModelAndView mv = this.interacaoService.getDefaultModelAndView(usuarioSistema.getUsuario(), intencaoCompra);
        return mv;
    }

    @RequestMapping(value = "salvarTelefoneRapido", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> salvarTelefone(@RequestBody @Valid Telefone telefone, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body(result.getFieldError("numeroUm").getDefaultMessage());
            }
            telefone = this.interacaoService.getPessoaService().atualizarTelefonePessoa(telefone);
            return ResponseEntity.ok(telefone);
        } catch (NegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "salvarInteracao")
    public ModelAndView salvarInteracao(Interacao interacao) {
        ModelAndView modelAndView = new ModelAndView("intencaoCompra/fragments/PropostaSucesso");
        try {
            this.interacaoService.salvarProposta(interacao);
        } catch (NegocioException e) {
            ModelAndView modelAndViewErro = new ModelAndView("intencaoCompra/fragments/PropostaErro");
            modelAndViewErro.addObject("mensagem", e.getMessage());
            return modelAndViewErro;
        }
        return modelAndView;
    }

}// fim
