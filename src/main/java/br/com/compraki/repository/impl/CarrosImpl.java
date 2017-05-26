package br.com.compraki.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.com.compraki.model.carro.Carro;
import br.com.compraki.repository.filter.CarroFilter;
import br.com.compraki.repository.paginacao.PageableQueries;
import br.com.compraki.repository.paginacao.PaginacaoUtil;

public class CarrosImpl implements PageableQueries<Carro, CarroFilter> {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Carro> filtrar(CarroFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Carro.class);
		criteria.createAlias("modelo", "modelo"); // inner join by default
		criteria.createAlias("modelo.fabricante", "fabricante");
		adicionarFiltros(filtro, criteria);
		paginacaoUtil.preparar(criteria, pageable);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	@Override
	public Long total(CarroFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Carro.class);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	@Override
	public void adicionarFiltros(CarroFilter filtro, Criteria criteria) {
		// TODO Auto-generated method stub

	}

}
