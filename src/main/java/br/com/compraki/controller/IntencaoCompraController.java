package br.com.compraki.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.compraki.enuns.PotenciaVeiculo;
import br.com.compraki.enuns.TipoCombustivel;
import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.enuns.UF;
import br.com.compraki.model.Cidade;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.carro.ModeloCarro;
import br.com.compraki.repository.Acessorios;
import br.com.compraki.repository.Cidades;
import br.com.compraki.repository.Fabricantes;
import br.com.compraki.repository.ModelosCarros;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.service.IntencaoService;
import br.com.compraki.service.NegocioException;
import br.com.compraki.validator.IntencaoValidator;

@Controller
@RequestMapping("/intencoes")
public class IntencaoCompraController {

    private static final String IT_VIEW = "intencaoCompra/IntencaoCompra";

    @Autowired
    private Fabricantes fabricantes;

    @Autowired
    private Cidades cidades;

    @Autowired
    private ModelosCarros modelosCarros;

    @Autowired
    private IntencaoValidator validator;

    @Autowired
    private IntencaoService intencaoService;

    @Autowired
    private Acessorios acessorios;

    @GetMapping("/novo")
    public ModelAndView novo(@AuthenticationPrincipal User user, IntencaoCompra intencaoCompra) {
        ModelAndView mv = getDefaultObjectsModelAndView(intencaoCompra, user);
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView salvar(@AuthenticationPrincipal User user, @Valid IntencaoCompra intencaoCompra,
            BindingResult result, RedirectAttributes attributes) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) user;
        validator.validate(intencaoCompra, result);
        if (result.hasErrors()) {
            this.novo(user, intencaoCompra);
        }
        try {

            this.intencaoService.salvar(intencaoCompra, usuarioSistema.getUsuario());
            attributes.addFlashAttribute("mensagem",
                    "Parabéns, sua intenção de compra foi salva com sucesso. Aguarde o resultado !");
            return new ModelAndView("redirect:/intencoes/novo");

        } catch (NegocioException e) {
            // result.addError(new ObjectError("IntencaoCompra",
            // e.getMessage()));
            return novo(user, intencaoCompra);
        }
    }

    @RequestMapping(value = "buscarModelos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<ModeloCarro> pesquisarPorCodigoFabricante(
            @RequestParam(name = "marca", defaultValue = "-1") Long codigoMarca) {
        return this.modelosCarros.findByFabricanteCodigo(codigoMarca);
    }

    @RequestMapping(value = "buscarCidades", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cidade> pesquisarCidadePorUf(
            @RequestParam(name = "uf", defaultValue = "-1") String siglaUf) {
        return this.cidades.findBySigla(siglaUf);
    }

    @RequestMapping(value = "/atualizaFormularioVeiculo", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView atualizaFormularioVeiculo(Long codigoIntencao, TipoVeiculo tipoVeiculo,
            @RequestParam(value = "cores[]", required = false) Long[] cores,
            @RequestParam(value = "acessorios[]", required = false) Long[] acessorios) {
        ModelAndView modelAndView = new ModelAndView("intencaoCompra/fragments/TipoCarro");
        modelAndView.addObject("intencaoCompra", new IntencaoCompra());
        return modelAndView;
    }

    private ModelAndView getDefaultObjectsModelAndView(IntencaoCompra intencaoCompra, User user) {
        ModelAndView modelAndView = new ModelAndView(IT_VIEW);
        modelAndView.addObject("fabricantes", this.fabricantes.findAll());
        modelAndView.addObject("cidades", this.cidades.findAll());
        modelAndView.addObject("acessorios", this.acessorios.findAll());
        modelAndView.addObject("tiposCombustivel", TipoCombustivel.values());
        modelAndView.addObject("ufs", UF.values());
        modelAndView.addObject("potencias", PotenciaVeiculo.values());
        modelAndView.addObject("tipos", TipoVeiculo.values());
        modelAndView.addObject("cores", this.intencaoService.getSelectedCores(intencaoCompra));
        return modelAndView;
    }

}// fim
