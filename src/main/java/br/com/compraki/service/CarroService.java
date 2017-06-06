package br.com.compraki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.compraki.enuns.CategoriaCarro;
import br.com.compraki.enuns.CategoriaMoto;
import br.com.compraki.enuns.CategoriaPesado;
import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Acessorio;
import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.model.veiculo.Fabricante;
import br.com.compraki.model.veiculo.ModeloVeiculo;
import br.com.compraki.repository.Acessorios;
import br.com.compraki.repository.Carros;
import br.com.compraki.repository.Cores;
import br.com.compraki.repository.Fabricantes;
import br.com.compraki.repository.ModelosVeiculos;

@Service
public class CarroService {

    @Autowired
    private Fabricantes fabricantes;

    @Autowired
    private Acessorios acessorios;

    @Autowired
    private Carros carros;

    @Autowired
    private ModelosVeiculos modelosVeiculos;

    @Autowired
    private Cores cores;

    public List<Acessorio> getSelectedAcessorrios(Carro carro) {
        List<Acessorio> acessorios = this.acessorios.findByTipoVeiculo(carro.getTipoVeiculo());
        int count = 0;
        int itensSelecionados = carro.getAcessorios() != null ? carro.getAcessorios().size() : 0;
        boolean tipodiferente = false;
        if(carro.getAcessorios() != null && carro.getAcessorios().size() > 0){
            for (Acessorio acessorio : carro.getAcessorios()) {
                if (!acessorio.getTipoVeiculo().equals(carro.getTipoVeiculo())) {
                    tipodiferente = true;
                    break;
                }
            }
        }
        if (itensSelecionados > 0 && !tipodiferente) {
            do {
                Acessorio acessorio = carro.getAcessorios().get(count);
                Long codigo = acessorio.getCodigo();
                int pos = acessorio.getPosicao();
                String descricao = acessorio.getDescricao();
                acessorios.set(pos - 1, new Acessorio(codigo, descricao, Boolean.TRUE));
                count++;
                if (count == itensSelecionados) {
                    break;
                }
            } while (count <= (itensSelecionados - 1));

        }
        return acessorios;
    }

    @Transactional
    public void salvarCarro(Carro carro, Usuario usuario) throws NegocioException {
        try {
            ModeloVeiculo modelo = this.modelosVeiculos.saveAndFlush(carro.getModelo());
            carro.setModelo(modelo);
            carro.setAcessorios(getAcessoriosGerenciados(carro));
            carro.setUsuario(usuario);
            this.carros.save(carro);
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    public Fabricante salvarMarcaRapido(Fabricante fabricante) throws NegocioException {
        try {
            if (this.fabricantes.findByNome(fabricante.getNome()).isPresent()) {
                throw new NegocioException("Marca já cadastrada");
            }
            return this.fabricantes.saveAndFlush(fabricante);
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private List<Acessorio> getAcessoriosGerenciados(Carro carro) {
        Long[] codigos = new Long[carro.getAcessorios().size()];
        int count = 0;
        for (Acessorio acessorio : carro.getAcessorios()) {
            codigos[count] = acessorio.getCodigo();
            count++;
        }
        return this.acessorios.findByCodigoIn(codigos);
    }

    public ModelAndView getModelAndViewTipoVeiculo(Long codigoCarro, TipoVeiculo tipoVeiculo, Long[] acessorios) {
        ModelAndView modelAndView = null;
        if (tipoVeiculo.equals(TipoVeiculo.CARRO)) {
            modelAndView = new ModelAndView("carro/fragments/TipoCarro");
            modelAndView.addObject("categorias", CategoriaCarro.values());
        } else if (tipoVeiculo.equals(TipoVeiculo.MOTO)) {
            modelAndView = new ModelAndView("carro/fragments/TipoMoto");
            modelAndView.addObject("categorias", CategoriaMoto.values());
        } else {
            modelAndView = new ModelAndView("carro/fragments/TipoPesado");
            modelAndView.addObject("categorias", CategoriaPesado.values());
        }
        Carro carro = null;
        carro = codigoCarro != null ? this.carros.getFullCar(codigoCarro) : new Carro();
        carro.setTipoVeiculo(tipoVeiculo);
        modelAndView.addObject("carro", carro);
        modelAndView.addObject("fabricantes", this.fabricantes.findByTipoVeiculo(tipoVeiculo));
        modelAndView.addObject("cores", this.cores.findAll());
        if (acessorios != null) {
            carro.setAcessorios(this.acessorios.findByCodigoIn(acessorios));
        }
        modelAndView.addObject("acessorios", getSelectedAcessorrios(carro));
        return modelAndView;
    }

    public void getFieldError(Carro carro, BindingResult result) {
        if (result.getFieldError("modelo.fabricante") != null) {
            carro.getCarroHelper().setErroMarca(Boolean.TRUE);
        } else {
            carro.getCarroHelper().setErroMarca(Boolean.FALSE);
            carro.getCarroHelper().setMarca(carro.getModelo().getFabricante().getCodigo());
        }
        if (result.getFieldError("modelo.descricao") != null) {
            carro.getCarroHelper().setErroModelo(Boolean.TRUE);
        } else {
            carro.getCarroHelper().setErroModelo(Boolean.FALSE);
            carro.getCarroHelper().setModelo(carro.getModelo().getDescricao());
        }
        if (result.getFieldError("modelo.categoria") != null) {
            carro.getCarroHelper().setErroCategoria(Boolean.TRUE);
        } else {
            carro.getCarroHelper().setErroCategoria(Boolean.FALSE);
            carro.getCarroHelper().setCategoria(carro.getModelo().getCategoria());
        }
        if (result.getFieldError("cor") != null) {
            carro.getCarroHelper().setErroCor(Boolean.TRUE);
        } else {
            carro.getCarroHelper().setErroCor(Boolean.FALSE);
            carro.getCarroHelper().setCor(carro.getCor().getCodigo());
        }
        if (result.getFieldError("acessorios") != null) {
            carro.getCarroHelper().setErroAcessorios(Boolean.TRUE);
        } else {
            carro.getCarroHelper().setErroAcessorios(Boolean.FALSE);
            carro.getCarroHelper().setAcessorios(carro.getCarroHelper().getAcessoriosJSON(carro.getAcessorios()));
        }
    }

    public Fabricantes getFabricantes() {
        return fabricantes;
    }

    public Acessorios getAcessorios() {
        return acessorios;
    }

    public Carros getCarros() {
        return carros;
    }

    public Cores getCores() {
        return cores;
    }

}