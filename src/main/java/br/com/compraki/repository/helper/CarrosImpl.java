package br.com.compraki.repository.helper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.compraki.model.carro.Carro;
import br.com.compraki.repository.filter.CarroFilter;
import br.com.compraki.repository.paginacao.PaginacaoUtil;

public class CarrosImpl implements CarrosQueries {

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
        if (!StringUtils.isEmpty(filtro.getCodigo())) {
            criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
        }
        if (!StringUtils.isEmpty(filtro.getModelo())) {
            criteria.add(Restrictions.ilike("cnpj", filtro.getModelo(), MatchMode.ANYWHERE));
        }
        if (!StringUtils.isEmpty(filtro.getFabricante())) {
            criteria.add(Restrictions.eq("modelo.fabricante", filtro.getFabricante()));
        }
        if (!StringUtils.isEmpty(filtro.getCor())) {
            criteria.add(Restrictions.ilike("cor", filtro.getCor(), MatchMode.ANYWHERE));
        }
    }

    @Transactional(readOnly = true)
    public Carro getFullCar(Long codigo) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(Carro.class);
        criteria.createAlias("modelo", "modelo");
        criteria.createAlias("modelo.fabricante", "fabricante");
        criteria.add(Restrictions.eq("codigo", codigo));
        Carro carro = (Carro) criteria.uniqueResult();
        Hibernate.initialize(carro.getAcessorios());
        return carro;
    }
}