package br.com.compraki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.repository.helper.CarrosQueries;

public interface Carros extends JpaRepository<Carro, Long>, CarrosQueries {
	
	@Query("SELECT c FROM Carro c INNER JOIN FETCH c.modelo WHERE c.usuario = (:usuario)")
    public List<Carro> findByUsuarioAndFetchEager(@Param("usuario") Usuario usuario);

}