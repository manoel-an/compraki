package br.com.compraki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.compraki.model.Grupo;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.repository.helper.CarrosQueries;

public interface Carros extends JpaRepository<Carro, Long>, CarrosQueries {
	
	public List<Carro> findByUsuario(Usuario usuario);
	
	@Query("SELECT c FROM Carro c INNER JOIN FETCH c.permissoes WHERE g.codigo = (:codigo)")
    public List<Grupo> findByCodigoAndFetchEager(@Param("codigo") Long codigo);

}