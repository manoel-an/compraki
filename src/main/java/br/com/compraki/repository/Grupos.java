package br.com.compraki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.compraki.model.Grupo;

public interface Grupos extends JpaRepository<Grupo, Long> {

    @Query("SELECT g FROM Grupo g INNER JOIN FETCH g.permissoes WHERE g.codigo = (:codigo)")
    public List<Grupo> findByCodigoAndFetchEager(@Param("codigo") Long codigo);
}