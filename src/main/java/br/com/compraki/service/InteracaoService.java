package br.com.compraki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.enuns.StatusInteracao;
import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Cor;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Interacao;
import br.com.compraki.model.Pessoa;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.repository.Carros;
import br.com.compraki.repository.IntencaoCompras;
import br.com.compraki.repository.Interacoes;
import br.com.compraki.repository.Pessoas;
import br.com.compraki.repository.Usuarios;

@Service
public class InteracaoService {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private Pessoas pessoas;

    @Autowired
    private Usuarios usuarios;

    @Autowired
    private Interacoes interacoes;

    @Autowired
    private IntencaoCompras intencaoCompras;

    @Autowired
    private Carros carros;

    private static final String IT_DETALHES_VIEW = "intencaoCompra/DetalhesIntencaoCompra";

    private static final String ITR_VIEW = "interacao/fornecedor/PropostaFornecedor";

    public ModelAndView getDefaultModelAndView(Usuario usuario, IntencaoCompra intencaoCompra) {
        ModelAndView modelAndView = new ModelAndView(IT_DETALHES_VIEW);
        ModelAndView mvProposta = new ModelAndView(ITR_VIEW);
        modelAndView.addObject("veiculos", carros.findByUsuarioAndFetchEager(usuario));
        modelAndView.addObject(mvProposta);
        modelAndView.addObject("tipos", TipoVeiculo.values());
        
        Carro carro = new Carro();
        carro.setUsuario(usuario);
        modelAndView.addObject("carro", carro);
        modelAndView.addObject("cadastroVeiculo", Boolean.FALSE);
        modelAndView.addObject("intencaoCompra", intencaoCompra);
        Interacao interacao = new Interacao();
        interacao.setValor(intencaoCompra.getValor());
        Pessoa pessoa = this.pessoas.findByUsuario(usuario).isPresent() ? this.pessoas.findByUsuario(usuario).get()
                : null;
        modelAndView.addObject("telefone", pessoa != null ? pessoa.getTelefone().getNumeroUm().replace(" ", "") : "");
        interacao.setCodigoPessoa(pessoa != null ? pessoa.getCodigo() : 0);
        interacao.setIntencaoCompra(intencaoCompra);
        interacao.setCodigoUsuario(intencaoCompra.getUsuario().getCodigo());
        modelAndView.addObject("interacao", interacao);
        boolean formProposta = usuario.getCodigo() != intencaoCompra.getUsuario().getCodigo();
        modelAndView.addObject("formProposta", formProposta);
        return modelAndView;
    }

    @Transactional
    public void salvarProposta(Interacao interacao) throws NegocioException {
        try {
            interacao.setIntencaoCompra(
                    this.intencaoCompras.getIntencaoCompraCompleto(interacao.getIntencaoCompra().getCodigo()));
            interacao.setFornecedor(this.usuarios.findByCodigo(interacao.getCodigoUsuario()));
            int resp = this.validaProposta(interacao);
            if (resp == 1) {
                throw new NegocioException("O Veículo informado não confere com a intenção de compra");
            } else if (resp == 2) {
                throw new NegocioException("O Veículo informado não possui a cor informada na intenção de compra");
            } else {
                interacao.setStatus(StatusInteracao.ENVIADA);
                this.interacoes.save(interacao);
            }
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public int validaProposta(Interacao interacao) {
        int retorno = 0;
        boolean possuiCor = false;
        interacao.setVeiculo(this.carros.getFullCar(interacao.getVeiculo().getCodigo()));
        if (!interacao.getVeiculo().getModelo().getDescricao().toUpperCase()
                .contains(interacao.getIntencaoCompra().getModelo().getDescricao().toUpperCase())) {
            retorno = 1;
        }
        for (Cor cor : interacao.getIntencaoCompra().getCores()) {
            if (cor.getDescricao().equals(interacao.getVeiculo().getCor().getDescricao())) {
                possuiCor = true;
            }
        }
        if (!possuiCor) {
            retorno = 2;
        }
        return retorno;
    }

    public PessoaService getPessoaService() {
        return pessoaService;
    }

}