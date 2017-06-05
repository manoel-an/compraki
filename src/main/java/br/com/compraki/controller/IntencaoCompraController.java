package br.com.compraki.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import br.com.compraki.repository.Cores;
import br.com.compraki.repository.Fabricantes;
import br.com.compraki.repository.ModelosCarros;
import br.com.compraki.repository.filter.IntencaoFilter;
import br.com.compraki.repository.paginacao.PageWrapper;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.service.IntencaoService;
import br.com.compraki.service.NegocioException;
import br.com.compraki.validator.IntencaoValidator;

@Controller
@RequestMapping("/intencoes")
public class IntencaoCompraController {

    private static final String IT_VIEW = "intencaoCompra/IntencaoCompra";

    private static final String IT_PESQUISA_VIEW = "intencaoCompra/PesquisaIntencoes";

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

    @Autowired
    private Cores cores;

    @GetMapping("/novo")
    public ModelAndView novo(IntencaoCompra intencaoCompra) {
        ModelAndView mv = getDefaultObjectsModelAndView(intencaoCompra);
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView salvar(@AuthenticationPrincipal User user, @Valid IntencaoCompra intencaoCompra,
            BindingResult result, RedirectAttributes attributes) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) user;
        validator.validate(intencaoCompra, result);
        if (result.hasErrors()) {
            this.intencaoService.getFieldError(intencaoCompra, result);
            intencaoCompra.getIntencaoHelper().setHasErrors(Boolean.TRUE);
            return this.novo(intencaoCompra);
        }
        try {

            this.intencaoService.salvar(intencaoCompra, usuarioSistema.getUsuario());
            attributes.addFlashAttribute("mensagem",
                    "Parabéns, sua intenção de compra foi salva com sucesso. Aguarde o resultado !");
            return new ModelAndView("redirect:/intencoes/novo");

        } catch (NegocioException e) {
            result.addError(new ObjectError("IntencaoCompra", e.getMessage()));
            return novo(intencaoCompra);
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
        ModelAndView modelAndView = this.intencaoService.getModelAndViewTipoVeiculo(codigoIntencao, tipoVeiculo,
                acessorios, cores);
        return modelAndView;
    }

    @GetMapping("/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") IntencaoCompra intencaoCompra) {
        ModelAndView modelAndView = this.novo(intencaoCompra);
        intencaoCompra.getIntencaoHelper().setTipoVeiculo(intencaoCompra.getTipoVeiculo());
        modelAndView.addObject("intencaoCompra", intencaoCompra);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView pesquisar(@AuthenticationPrincipal User user, IntencaoFilter intencaoFilter,
            BindingResult result, @PageableDefault(size = 7) Pageable pageable, HttpServletRequest httpServletRequest) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) user;
        ModelAndView mv = new ModelAndView(IT_PESQUISA_VIEW);
        PageWrapper<IntencaoCompra> paginaWrapper = new PageWrapper<>(this.intencaoService.getIntencaoCompras()
                .filtrar(usuarioSistema.getUsuario(), intencaoFilter, pageable), httpServletRequest);
        mv.addObject("fabricantes", this.fabricantes.findAll());
        mv.addObject("tipoVeiculos", TipoVeiculo.values());
        mv.addObject("cores", this.cores.findAll());
        mv.addObject("acessorios", this.acessorios.findAll());
        mv.addObject("tiposCombustivel", TipoCombustivel.values());
        mv.addObject("ufs", UF.values());
        mv.addObject("potencias", PotenciaVeiculo.values());
        mv.addObject("pagina", paginaWrapper);
        return mv;
    }

    private ModelAndView getDefaultObjectsModelAndView(IntencaoCompra intencaoCompra) {
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
