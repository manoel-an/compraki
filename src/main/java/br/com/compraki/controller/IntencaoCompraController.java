package br.com.compraki.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import br.com.compraki.dto.IntencaoDTO;
import br.com.compraki.enuns.PotenciaVeiculo;
import br.com.compraki.enuns.TipoCombustivel;
import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.enuns.UF;
import br.com.compraki.model.Cidade;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.veiculo.ModeloVeiculo;
import br.com.compraki.repository.Acessorios;
import br.com.compraki.repository.Cidades;
import br.com.compraki.repository.Cores;
import br.com.compraki.repository.Fabricantes;
import br.com.compraki.repository.ModelosVeiculos;
import br.com.compraki.repository.filter.IntencaoFilter;
import br.com.compraki.repository.paginacao.Pager;
import br.com.compraki.repository.paginacao.Pagination;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.service.IntencaoService;
import br.com.compraki.service.NegocioException;
import br.com.compraki.validator.IntencaoValidator;

@Controller
@RequestMapping("/intencoes")
public class IntencaoCompraController extends Pagination {

	private static final String IT_VIEW = "intencaoCompra/IntencaoCompra";
	private static final String IT_PESQUISA_VIEW = "intencaoCompra/PesquisaIntencoes";

	@Autowired
	private Fabricantes fabricantes;

	@Autowired
	private Cidades cidades;

	@Autowired
	private ModelosVeiculos modelosVeiculos;

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
	public @ResponseBody List<ModeloVeiculo> pesquisarPorCodigoFabricante(
			@RequestParam(name = "marca", defaultValue = "-1") Long codigoMarca) {
		return this.modelosVeiculos.findByFabricanteCodigo(codigoMarca);
	}

	@RequestMapping(value = "buscarCidades", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarCidadePorUf(
			@RequestParam(name = "uf", defaultValue = "-1") String siglaUf) {
		return this.cidades.findBySigla(siglaUf);
	}

	@RequestMapping(value = "/atualizaFormularioVeiculo", consumes = { MediaType.APPLICATION_JSON_VALUE })
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
	public ModelAndView showPersonsPage(@AuthenticationPrincipal User user, IntencaoFilter intencaoFilter,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page,
			HttpServletRequest httpServletRequest) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		ModelAndView mv = new ModelAndView(IT_PESQUISA_VIEW);
		Page<IntencaoCompra> intencoes = this.intencaoService.getIntencaoCompras().filtrar(usuarioSistema.getUsuario(),
				intencaoFilter, new PageRequest(getEvalPage(page), getEvalPageSize(pageSize), getSort(sort)));
		Pager<IntencaoCompra> pager = new Pager<IntencaoCompra>(intencoes, httpServletRequest);
		mv.addObject("pagina", intencoes);
		mv.addObject("selectedPageSize", getEvalPageSize(pageSize));
		mv.addObject("pageSizes", PAGE_SIZES);
		mv.addObject("pager", pager);
		mv.addObject("fabricantes", this.fabricantes.findAll());
		mv.addObject("tipoVeiculos", TipoVeiculo.values());
		mv.addObject("cores", this.cores.findAll());
		mv.addObject("acessorios", this.acessorios.findAll());
		mv.addObject("tiposCombustivel", TipoCombustivel.values());
		mv.addObject("ufs", UF.values());
		mv.addObject("potencias", PotenciaVeiculo.values());
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

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<IntencaoDTO> pesquisar(String modeloCidade, Long codigoUsuario) {
		return this.intencaoService.getIntencaoCompras().porModeloOuCidade(modeloCidade, codigoUsuario);
	}

}// fim
