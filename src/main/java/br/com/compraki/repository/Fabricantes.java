package br.com.compraki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.carro.Fabricante;

public interface Fabricantes extends JpaRepository<Fabricante, Long> {

}