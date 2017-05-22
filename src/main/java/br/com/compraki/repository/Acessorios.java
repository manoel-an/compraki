package br.com.compraki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.carro.Acessorio;

public interface Acessorios extends JpaRepository<Acessorio, Long> {

}