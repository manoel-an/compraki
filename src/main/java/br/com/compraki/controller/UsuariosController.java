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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.compraki.enuns.EnumSexo;
import br.com.compraki.enuns.TipoPessoa;
import br.com.compraki.enuns.UF;
import br.com.compraki.model.Pessoa;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.service.PessoaService;
import br.com.compraki.validator.PessoaValidator;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private PessoaValidator validator;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/novo")
    public ModelAndView novo(@AuthenticationPrincipal User user, Pessoa pessoa) {
        ModelAndView modelAndView = getDefaultObjectsModelAndView(pessoa, user);
        return modelAndView;
    }

    @PostMapping("/novo")
    public ModelAndView salvarCadastro(@AuthenticationPrincipal User user, @Valid Pessoa pessoa, BindingResult result,
            RedirectAttributes attributes) {
        validator.validate(pessoa, result);
        if (result.hasErrors()) {
            if (result.getFieldError("nome") != null && pessoa.getInputTipoPessoa().equals("inputPessoaFISICA")) {
                pessoa.setErroNome(Boolean.TRUE);
            }
            if (result.getFieldError("nome") != null && pessoa.getInputTipoPessoa().equals("inputPessoaJURIDICA")) {
                pessoa.setErroRazaoSocial(Boolean.TRUE);
            }
            if (result.getFieldError("dataNascimento") != null) {
                pessoa.setErroDataNascimento(Boolean.TRUE);
            }
            if (result.getFieldError("cpfOuCnpj") != null && pessoa.getInputTipoPessoa().equals("inputPessoaFISICA")) {
                pessoa.setErroCpf(Boolean.TRUE);
            }
            if (result.getFieldError("cpfOuCnpj") != null
                    && pessoa.getInputTipoPessoa().equals("inputPessoaJURIDICA")) {
                pessoa.setErroCnpj(Boolean.TRUE);
            }
            if (result.getFieldError("nomeFantasia") != null
                    && pessoa.getInputTipoPessoa().equals("inputPessoaJURIDICA")) {
                pessoa.setErroNomeFantasia(Boolean.TRUE);
            }
            if (result.getFieldError("endereco.cep") != null) {
                System.out.println("oi");
            }
            return novo(user, pessoa);
        }
        return null;

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

    private ModelAndView getDefaultObjectsModelAndView(Pessoa pessoa, User user) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) user;
        ModelAndView modelAndView = new ModelAndView("usuario/CadastroUsuario");
        pessoa.setUsuario(usuarioSistema.getUsuario());
        pessoa.setEmail(usuarioSistema.getUsuario().getEmail());
        modelAndView.addObject("tipos", TipoPessoa.values());
        modelAndView.addObject("estados", UF.values());
        modelAndView.addObject("grupos", this.pessoaService.getGruposByRole(user));
        modelAndView.addObject("pessoa", pessoa);
        return modelAndView;
    }
}
