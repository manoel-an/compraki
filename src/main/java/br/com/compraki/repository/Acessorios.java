package br.com.compraki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.enuns.TipoVeiculo;
import br.com.compraki.model.carro.Acessorio;
import br.com.compraki.repository.helper.AcessoriosQueries;

public interface Acessorios extends JpaRepository<Acessorio, Long>, AcessoriosQueries {

	public Optional<Acessorio> findByDescricaoAndTipoVeiculo(String descricao, TipoVeiculo tipoVeiculo);

	public List<Acessorio> findByTipoVeiculo(TipoVeiculo tipoVeiculo);

	public List<Acessorio> findByCodigoIn(Long[] codigos);

}