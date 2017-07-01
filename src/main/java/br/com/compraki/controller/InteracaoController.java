package br.com.compraki.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.model.Interacao;
import br.com.compraki.repository.Carros;
import br.com.compraki.repository.Fabricantes;
import br.com.compraki.repository.Interacoes;
import br.com.compraki.repository.ModelosVeiculos;
import br.com.compraki.repository.filter.IntencaoFilter;

@Controller
@RequestMapping("/interacao")
public class InteracaoController {

	private static final String PROPOSTA_ENVIADA_VIEW = "interacao/PesquisaPropostas";
	
	
	@Autowired
	private Interacoes interacoes;
	
	@Autowired
    private Fabricantes fabricantes;
	
	@Autowired
    private Carros veiculos;
	
	@Autowired
    private ModelosVeiculos modelos;
	
	/*@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") IntencaoCompra intencaoCompra,
			@AuthenticationPrincipal User user, Interacao propostaFonecedor) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) user;
		ModelAndView mv = getDefaultObjectsModelAndViewDeProposta(usuarioSistema.getUsuario(), intencaoCompra,
				propostaFonecedor);
		return mv;
	}*/
	
	@GetMapping("/propostas")
    public ModelAndView pesquisar(@AuthenticationPrincipal User user, IntencaoFilter intencaoFilter,
            BindingResult result, @PageableDefault(size = 7) Pageable pageable, HttpServletRequest httpServletRequest) {
        //UsuarioSistema usuarioSistema = (UsuarioSistema) user;
        List<Interacao> listaPropostasEnviadas = interacoes.findAll();
        ModelAndView mv = new ModelAndView(PROPOSTA_ENVIADA_VIEW);
        mv.addObject("fabricantes", this.fabricantes.findAll());
        mv.addObject("veiculos", this.veiculos.findAll());
        mv.addObject("modelos", modelos.findAll());
        /*PageWrapper<Interacao> paginaWrapper = new PageWrapper<>(this.intencaoService.getIntencaoCompras()
                .filtrar(usuarioSistema.getUsuario(), intencaoFilter, pageable), httpServletRequest);
        mv.addObject("fabricantes", this.fabricantes.findAll());
        mv.addObject("tipoVeiculos", TipoVeiculo.values());
        mv.addObject("cores", this.cores.findAll());
        mv.addObject("acessorios", this.acessorios.findAll());
        mv.addObject("tiposCombustivel", TipoCombustivel.values());
        mv.addObject("ufs", UF.values());
        mv.addObject("potencias", PotenciaVeiculo.values());
        mv.addObject("pagina", paginaWrapper);*/
        mv.addObject("todasPropostasEnviadas", listaPropostasEnviadas);
        
        return mv;
    }
	
		
}// fim
