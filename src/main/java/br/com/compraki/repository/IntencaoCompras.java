package br.com.compraki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.repository.helper.IntencoesQueries;

public interface IntencaoCompras extends JpaRepository<IntencaoCompra, Long>, IntencoesQueries {

    @Query("SELECT i FROM IntencaoCompra i INNER JOIN FETCH i.modelo INNER JOIN FETCH i.cores WHERE i.codigo = :codigo")
    public List<IntencaoCompra> BuscaIntencoes(@Param("codigo") Long codigo);

    public Optional<IntencaoCompra> findByCodigo(Long codigo);
}
