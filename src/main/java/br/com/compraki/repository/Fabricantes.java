package br.com.compraki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.carro.Fabricante;

public interface Fabricantes extends JpaRepository<Fabricante, Long> {

	public Optional<Fabricante> findByNome(String nome);

}