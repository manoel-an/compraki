package br.com.compraki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.carro.Carro;
import br.com.compraki.repository.filter.CarroFilter;
import br.com.compraki.repository.paginacao.PageableQueries;

public interface Carros extends JpaRepository<Carro, Long>, PageableQueries<Carro, CarroFilter> {

}