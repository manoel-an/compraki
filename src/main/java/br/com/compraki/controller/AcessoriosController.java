package br.com.compraki.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.veiculo.Acessorio;
import br.com.compraki.repository.filter.AcessorioFilter;
import br.com.compraki.repository.paginacao.Pager;
import br.com.compraki.repository.paginacao.Pagination;
import br.com.compraki.service.AcessorioService;
import br.com.compraki.service.NegocioException;

@Controller
@RequestMapping("/acessorios")
public class AcessoriosController extends Pagination {

	@Autowired
	private AcessorioService acessorioService;

	@RequestMapping(value = "salvarAcessorio", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvarAcessorio(@RequestBody @Valid Acessorio acessorio,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
				return ResponseEntity.badRequest().body(result.getFieldError("descricao").getDefaultMessage());
			}
			acessorio = this.acessorioService.salvarAcessorio(acessorio);
			return ResponseEntity.ok(acessorio);
		} catch (NegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ModelAndView pesquisar(AcessorioFilter acessorioFilter, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, HttpServletRequest httpServletRequest,
			@RequestParam(value = "sort", required = false) String sort, boolean novoAcessorio) {
		ModelAndView mv = new ModelAndView("acessorio/PesquisaAcessorios");
		Page<Acessorio> acessorios = this.acessorioService.getAcessorios().filtrar(acessorioFilter,
				new PageRequest(getEvalPage(page), getEvalPageSize(pageSize), getSort(sort)));
		Pager<Acessorio> pager = new Pager<Acessorio>(acessorios, httpServletRequest);
		mv.addObject("pagina", acessorios);
		mv.addObject("selectedPageSize", getEvalPageSize(pageSize));
		mv.addObject("pageSizes", PAGE_SIZES);
		mv.addObject("pager", pager);
		mv.addObject("novoAcessorio", novoAcessorio);
		mv.addObject("tipos", TipoVeiculo.values());
		mv.addObject("tipoVeiculos", TipoVeiculo.values());
		return mv;
	}

}
