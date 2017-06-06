package br.com.compraki.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.compraki.model.veiculo.Acessorio;
import br.com.compraki.repository.Acessorios;

@Service
public class AcessorioService {

	@Autowired
	private Acessorios acessorios;

	@Transactional
	public Acessorio salvarAcessorio(Acessorio acessorio) throws NegocioException {
		try {
			Optional<Acessorio> op = this.acessorios.findByDescricaoAndTipoVeiculo(acessorio.getDescricao(),
					acessorio.getTipoVeiculo());
			Acessorio ac = null;
			if (op.isPresent()) {
				ac = op.get();
			}
			if (ac != null && acessorio.getCodigo() != ac.getCodigo()) {
				throw new NegocioException("Acessório já cadastrado");
			}
			if (acessorio.getPosicao() == null) {
				int posicao = this.acessorios.countByTipoVeiculo(acessorio.getTipoVeiculo()).intValue();
				acessorio.setPosicao(posicao == 0 ? 1 : (posicao + 1));
			}
			return this.acessorios.saveAndFlush(acessorio);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	public Acessorios getAcessorios() {
		return acessorios;
	}

}