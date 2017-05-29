package br.com.compraki.repository.helper;

import org.hibernate.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.compraki.model.carro.Carro;
import br.com.compraki.repository.filter.AcessorioFilter;

public interface AcessoriosQueries {

    public Page<Carro> filtrar(AcessorioFilter filtro, Pageable pageable);

    public Long total(AcessorioFilter filtro);

    public void adicionarFiltros(AcessorioFilter filtro, Criteria criteria);

}