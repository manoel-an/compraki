package br.com.compraki.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.compraki.enuns.EnumSexo;
import br.com.compraki.enuns.TipoPessoa;
import br.com.compraki.enuns.UF;
import br.com.compraki.model.Pessoa;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.service.NegocioException;
import br.com.compraki.service.PessoaService;
import br.com.compraki.validator.PessoaValidator;

@Controller
@RequestMapping("/usuarios")
public class PessoasController {

    @Autowired
    private PessoaValidator validator;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/novo")
    public ModelAndView novo(@AuthenticationPrincipal User user, Pessoa pessoa, Boolean hasErrors) {
        hasErrors = hasErrors == null ? Boolean.FALSE : (hasErrors != null && hasErrors ? Boolean.TRUE : Boolean.FALSE);
        ModelAndView modelAndView = getDefaultObjectsModelAndView(pessoa, user, hasErrors);
        return modelAndView;
    }

    @PostMapping("/novo")
    public ModelAndView salvarPessoa(@AuthenticationPrincipal User user, @Valid Pessoa pessoa, BindingResult result,
            RedirectAttributes attributes) {
        validator.validate(pessoa, result);
        if (result.hasErrors()) {
            this.pessoaService.getFieldError(pessoa, result);
            return novo(user, pessoa, Boolean.TRUE);
        }
        try {
            pessoa = this.pessoaService.salvarPessoa(pessoa);
        } catch (NegocioException e) {
            result.addError(new ObjectError("Pessoa", e.getMessage()));
        }
        attributes.addFlashAttribute("mensagem", "Usuario atualizado com sucesso !");
        return new ModelAndView("redirect:/usuarios/novo");
    }

    @RequestMapping(value = "/atualizaFormularioPessoaFisica", method = RequestMethod.POST)
    public ModelAndView atualizaFormularioPessoaFisica() {
        ModelAndView modelAndView = new ModelAndView("usuario/fragments/DadosPessoais");
        modelAndView.addObject("sexos", EnumSexo.values());
        modelAndView.addObject("pessoa", new Pessoa());
        return modelAndView;
    }

    @RequestMapping(value = "/atualizaFormularioPessoaJuridica", method = RequestMethod.POST)
    public ModelAndView atualizaFormularioPessoaJuridica() {
        ModelAndView modelAndView = new ModelAndView("usuario/fragments/DadosEmpresa");
        modelAndView.addObject("pessoa", new Pessoa());
        return modelAndView;
    }

    private ModelAndView getDefaultObjectsModelAndView(Pessoa pessoa, User user, boolean hasErrors) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) user;
        ModelAndView modelAndView = new ModelAndView("usuario/CadastroPessoa");
        pessoa.setUsuario(usuarioSistema.getUsuario());
        pessoa.setEmail(usuarioSistema.getUsuario().getEmail());
        modelAndView.addObject("tipos", TipoPessoa.values());
        modelAndView.addObject("estados", UF.values());
        modelAndView.addObject("grupos", this.pessoaService.getGruposByRole(user));
        Pessoa p = this.pessoaService.getPessoaByUsuarioLogado(usuarioSistema.getUsuario());
        modelAndView.addObject("pessoa", p.getCodigo() != null && !hasErrors ? p : pessoa);
        return modelAndView;
    }
}
