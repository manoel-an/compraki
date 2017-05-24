package br.com.compraki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.carro.Carro;

public interface Carros extends JpaRepository<Carro, Long> {

}