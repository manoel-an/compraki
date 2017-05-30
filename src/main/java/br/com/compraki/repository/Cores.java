package br.com.compraki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.Cor;

public interface Cores extends JpaRepository<Cor, Long> {

	public List<Cor> findByCodigoIn(Long[] codigos);

}