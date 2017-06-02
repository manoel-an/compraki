package br.com.compraki.repository.helper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import br.com.compraki.model.IntencaoCompra;

public class IntencoesComprasImpl implements IntencoesQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional(readOnly = true)
    public IntencaoCompra getIntencaoCompraCompleto(Long codigo) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(IntencaoCompra.class);
        criteria.createAlias("modelo", "modelo");
        criteria.createAlias("modelo.fabricante", "fabricante");
        criteria.add(Restrictions.eq("codigo", codigo));
        IntencaoCompra intencaoCompra = (IntencaoCompra) criteria.uniqueResult();
        Hibernate.initialize(intencaoCompra.getAcessorios());
        Hibernate.initialize(intencaoCompra.getCores());
        return intencaoCompra;
    }
}