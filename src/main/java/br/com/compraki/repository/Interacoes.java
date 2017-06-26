package br.com.compraki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.Interacao;

public interface Interacoes extends JpaRepository<Interacao, Long> {

}