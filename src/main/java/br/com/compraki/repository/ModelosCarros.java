package br.com.compraki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.carro.ModeloCarro;

public interface ModelosCarros extends JpaRepository<ModeloCarro, Long> {

	public List<ModeloCarro> findByFabricanteCodigo(Long codigoFabricante);

	public ModeloCarro findByCodigo(Long codigo);
	
	
}