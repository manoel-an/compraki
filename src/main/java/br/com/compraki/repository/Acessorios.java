package br.com.compraki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.carro.Acessorio;
import br.com.compraki.repository.helper.AcessoriosQueries;

public interface Acessorios extends JpaRepository<Acessorio, Long>, AcessoriosQueries {

    public List<Acessorio> findByCodigoIn(Long[] codigos);

}