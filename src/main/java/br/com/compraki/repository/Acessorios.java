package br.com.compraki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.carro.Acessorio;

public interface Acessorios extends JpaRepository<Acessorio, Long> {

	public List<Acessorio> findByCodigoIn(Long[] codigos);

}