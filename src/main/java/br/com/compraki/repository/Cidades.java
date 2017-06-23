package br.com.compraki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.Cidade;

public interface Cidades extends JpaRepository<Cidade, Long> {

    public Optional<Cidade> findByCodigo(Long codigo);

    public List<Cidade> findBySigla(String ufSigla);

}
