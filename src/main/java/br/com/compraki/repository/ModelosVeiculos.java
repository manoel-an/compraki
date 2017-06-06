package br.com.compraki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.veiculo.ModeloVeiculo;

public interface ModelosVeiculos extends JpaRepository<ModeloVeiculo, Long> {

	public List<ModeloVeiculo> findByFabricanteCodigo(Long codigoFabricante);

	public ModeloVeiculo findByCodigo(Long codigo);
	
	
}