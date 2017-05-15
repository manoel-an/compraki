package br.com.compraki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.Grupo;

public interface Grupos extends JpaRepository<Grupo, Long> {

}