package br.com.compraki.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.compraki.model.Cor;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.carro.ModeloCarro;
import br.com.compraki.repository.Cores;
import br.com.compraki.repository.IntencaoCompras;
import br.com.compraki.repository.ModelosCarros;

@Service
public class IntencaoService {

    @Autowired
    private IntencaoCompras intencaoCompras;
    @Autowired
    private ModelosCarros modelos;
    @Autowired
    private Cores cores;

    @Transactional
    public void salvar(IntencaoCompra intencaoCompra, Usuario usuario) throws NegocioException {
        try {
            setObjects(intencaoCompra, usuario);
            if (intencaoCompra != null && intencaoCompra.getUsuario() != null) {
                this.intencaoCompras.save(intencaoCompra);
            }
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }

    }

    public void setObjects(IntencaoCompra intencaoCompra, Usuario usuario) {
        // seta Usuario
        // Não precisa deste trecho aqui Claudio
        // Usuario usuarioFind = usuarios.findByCodigo(usuario.getCodigo());
        // Pode atribuir o usuario diretamente; pois o spring security ja
        // disponibiliza o mesmo a qualquer tempo
        intencaoCompra.setUsuario(usuario);

        // busca e setaModelo
        ModeloCarro modelo = this.modelos.findByCodigo(intencaoCompra.getModelo().getCodigo());
        intencaoCompra.setModelo(modelo);

        // seta data
        intencaoCompra.setDataCriacao(new Date());
        // seta Cores
        intencaoCompra.setCores(getCoresGerenciadas(intencaoCompra));
    }

    public Object getSelectedCores(IntencaoCompra intencaoCompra) {
        List<Cor> cores = this.cores.findAll();
        int count = 0;
        int itensSelecionados = intencaoCompra.getCores() != null ? intencaoCompra.getCores().size() : 0;
        if (itensSelecionados > 0) {
            do {
                Cor cor = intencaoCompra.getCores().get(count);
                Long codigo = cor.getCodigo();
                int pos = cor.getCodigo().intValue();
                String descricao = cor.getDescricao();
                cores.set(pos - 1, new Cor(codigo, descricao, Boolean.TRUE));
                count++;
                if (count == itensSelecionados) {
                    break;
                }
            } while (count <= (itensSelecionados - 1));

        }
        return cores;
    }

    private List<Cor> getCoresGerenciadas(IntencaoCompra intencaoCompra) {
        Long[] codigos = new Long[intencaoCompra.getCores().size()];
        int count = 0;
        for (Cor cor : intencaoCompra.getCores()) {
            codigos[count] = cor.getCodigo();
            count++;
        }
        return this.cores.findByCodigoIn(codigos);
    }

    public Cores getCores() {
        return cores;
    }

}// fim