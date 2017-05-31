package br.com.compraki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.carro.Fabricante;

public interface Fabricantes extends JpaRepository<Fabricante, Long> {

	public Optional<Fabricante> findByNome(String nome);

	public List<Fabricante> findByTipoVeiculo(TipoVeiculo tipoVeiculo);

}