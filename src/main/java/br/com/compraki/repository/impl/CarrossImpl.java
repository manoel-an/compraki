package br.com.compraki.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.compraki.model.carro.Carro;
import br.com.compraki.repository.filter.CarroFilter;
import br.com.compraki.repository.paginacao.PageableQueries;
import br.com.compraki.repository.paginacao.PaginacaoUtil;

public class CarrossImpl implements PageableQueries<Carro, CarroFilter> {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @Override
    public Page<Carro> filtrar(CarroFilter filtro, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long total(CarroFilter filtro) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void adicionarFiltros(CarroFilter filtro, Criteria criteria) {
        // TODO Auto-generated method stub

    }

}
