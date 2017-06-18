package br.com.compraki.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.model.veiculo.Fabricante;
import br.com.compraki.repository.filter.CarroFilter;
import br.com.compraki.repository.paginacao.PageWrapper;
import br.com.compraki.security.UsuarioSistema;
import br.com.compraki.service.CarroService;
import br.com.compraki.service.NegocioException;
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

	@RequestMapping(value = "salvarCarroIntermediario")
	public ModelAndView salvarCarroIntermediario(Carro carro) {
		try {
			ModelAndView modelAndView = new ModelAndView("carro/fragments/CarroIntermediario");
			carro = this.carroService.salvarCarroIntermiediario(carro);
			modelAndView.addObject("carro", carro);
			return modelAndView;
		} catch (NegocioException e) {
			return null;
		}
	}

	@PostMapping("/novo")
	public ModelAndView salvarCarro(@AuthenticationPrincipal User user, @Valid Carro carro, BindingResult result,
			RedirectAttributes attributes) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		validator.validate(carro, result);
		if (result.hasErrors()) {
			this.carroService.getFieldError(carro, result);
			carro.getCarroHelper().setHasErrors(Boolean.TRUE);
			return this.novo(carro);
		}
		try {
			this.carroService.salvarCarro(carro, usuarioSistema.getUsuario());
			attributes.addFlashAttribute("mensagem", "Veículo gravado com sucesso !");
			return new ModelAndView("redirect:/carros/novo");
		} catch (NegocioException e) {
			result.addError(new ObjectError("Carro", e.getMessage()));
			carro.getCarroHelper().setHasErrors(Boolean.TRUE);
			return novo(carro);
		}
	}

	@RequestMapping(value = "salvarMarcaRapido", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvarMarca(@RequestBody @Valid Fabricante fabricante,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
			}
			fabricante = this.carroService.salvarMarcaRapido(fabricante);
			return ResponseEntity.ok(fabricante);
		} catch (NegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Carro carro) {
		ModelAndView modelAndView = this.novo(carro);
		modelAndView.addObject("carro", carro);
		return modelAndView;
	}

	@GetMapping
	public ModelAndView pesquisar(@AuthenticationPrincipal User user, CarroFilter carroFilter, BindingResult result,
			@PageableDefault(size = 7) Pageable pageable, HttpServletRequest httpServletRequest) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		ModelAndView mv = new ModelAndView("carro/PesquisaCarros");
		PageWrapper<Carro> paginaWrapper = new PageWrapper<>(
				this.carroService.getCarros().filtrar(usuarioSistema.getUsuario(), carroFilter, pageable),
				httpServletRequest);
		mv.addObject("fabricantes", this.carroService.getFabricantes().findAll());
		mv.addObject("cores", this.carroService.getCores().findAll());
		mv.addObject("tipoVeiculos", TipoVeiculo.values());
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@RequestMapping(value = "/atualizaFormularioVeiculo", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ModelAndView atualizaFormularioVeiculo(Long codigoCarro, TipoVeiculo tipoVeiculo,
			@RequestParam(value = "acessorios[]", required = false) Long[] acessorios, boolean cadastroRapidoMarca) {
		ModelAndView modelAndView = this.carroService.getModelAndViewTipoVeiculo(codigoCarro, tipoVeiculo, acessorios);
		modelAndView.addObject("cadastroRapidoMarca", cadastroRapidoMarca);
		return modelAndView;
	}

	private ModelAndView getDefaultObjectsModelAndView(Carro carro) {
		ModelAndView modelAndView = new ModelAndView("carro/CadastroCarro");
		modelAndView.addObject("tipos", TipoVeiculo.values());
		modelAndView.addObject("carro", carro);
		modelAndView.addObject("cadastroVeiculo", Boolean.TRUE);
		return modelAndView;
	}
}
