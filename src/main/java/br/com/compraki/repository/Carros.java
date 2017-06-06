package br.com.compraki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.repository.helper.CarrosQueries;

public interface Carros extends JpaRepository<Carro, Long>, CarrosQueries {

}