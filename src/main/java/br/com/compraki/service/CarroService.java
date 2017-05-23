package br.com.compraki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compraki.model.carro.Acessorio;
import br.com.compraki.model.carro.Carro;
import br.com.compraki.repository.Acessorios;
import br.com.compraki.repository.Fabricantes;

@Service
public class CarroService {

    @Autowired
    private Fabricantes fabricantes;

    @Autowired
    private Acessorios acessorios;

    public List<Acessorio> getSelectedAcessorrios(Carro carro) {
        List<Acessorio> acessorios = this.acessorios.findAll();
        int count = 0;
        int itensSelecionados = carro.getAcessorios() != null ? carro.getAcessorios().size() : 0;
        if (itensSelecionados > 0) {
            do {
                Acessorio acessorio = carro.getAcessorios().get(count);
                Long codigo = acessorio.getCodigo();
                int pos = acessorio.getCodigo().intValue();
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

    public Fabricantes getFabricantes() {
        return fabricantes;
    }

    public Acessorios getAcessorios() {
        return acessorios;
    }

}