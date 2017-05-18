package br.com.compraki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.IntencaoCompra;

public interface IntencaoCompras extends JpaRepository<IntencaoCompra, Long> {

	

}
