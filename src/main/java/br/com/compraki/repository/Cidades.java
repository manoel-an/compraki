package br.com.compraki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.compraki.model.Cidade;

public interface Cidades extends JpaRepository<Cidade, Long	> {

    @Query("SELECT u FROM Cidade u WHERE u.sigla = (:ufSigla) ") 
	public List<Cidade> findBySigla(@Param("ufSigla")String ufSigla);
	

}
