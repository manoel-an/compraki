package br.com.compraki.repository.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.compraki.model.Cor;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Usuario;
import br.com.compraki.model.carro.Acessorio;
import br.com.compraki.repository.filter.IntencaoFilter;
import br.com.compraki.repository.paginacao.PaginacaoUtil;

public class IntencaoComprasImpl implements IntencoesQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public Page<IntencaoCompra> filtrar(Usuario usuario, IntencaoFilter filtro, Pageable pageable) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(IntencaoCompra.class);
        criteria.createAlias("modelo", "modelo"); // inner join by default
        criteria.createAlias("modelo.fabricante", "fabricante");
        adicionarFiltros(usuario, filtro, criteria);
        paginacaoUtil.preparar(criteria, pageable);

        List<IntencaoCompra> filtrados = criteria.list();
        filtrados.forEach(it -> Hibernate.initialize(it.getCores()));
        filtrados.forEach(it -> Hibernate.initialize(it.getAcessorios()));

        return new PageImpl<>(filtrados, pageable, total(filtro));
    }

    @Override
    public Long total(IntencaoFilter filtro) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(IntencaoCompra.class);
        if (!StringUtils.isEmpty(filtro.getTipoVeiculo())) {
            criteria.add(Restrictions.eq("tipoVeiculo", filtro.getTipoVeiculo()));
        }
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public void adicionarFiltros(Usuario usuario, IntencaoFilter filtro, Criteria criteria) {
        criteria.add(Restrictions.eq("usuario", usuario));

        if (!StringUtils.isEmpty(filtro.getValorInicial())) {
            criteria.add(Restrictions.ge("valor", filtro.getValorInicial()));
        }
        if (!StringUtils.isEmpty(filtro.getValorFinal())) {
            criteria.add(Restrictions.le("valor", filtro.getValorFinal()));
        }

        if (!StringUtils.isEmpty(filtro.getCombustivel())) {
            criteria.add(Restrictions.eq("tipoCombustivel", filtro.getCombustivel().toString()));
        }

        if (!StringUtils.isEmpty(filtro.getPotencia())) {
            criteria.add(Restrictions.eq("potencia", filtro.getPotencia().toString()));
        }

        if (!StringUtils.isEmpty(filtro.getCodigo())) {
            criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
        }

        if (!StringUtils.isEmpty(filtro.getCidadePreferencia())) {
            criteria.add(Restrictions.eq("cidadePreferencia", filtro.getCidadePreferencia().getCodigo().toString()));
        }

        if (!StringUtils.isEmpty(filtro.getModelo())) {
            criteria.add(Restrictions.eq("modelo", filtro.getModelo()));
        }
        if (!StringUtils.isEmpty(filtro.getFabricante())) {
            criteria.add(Restrictions.eq("modelo.fabricante", filtro.getFabricante()));
        }
        if (!StringUtils.isEmpty(filtro.getTipoVeiculo())) {
            criteria.add(Restrictions.eq("tipoVeiculo", filtro.getTipoVeiculo()));
        }
        if (filtro.getCores() != null && !filtro.getCores().isEmpty()) {
            criteria.createAlias("cores", "cor");
            List<Long> codigos = new ArrayList<Long>(0);
            for (Cor cor : filtro.getCores()) {
                if (cor != null) {
                    codigos.add(cor.getCodigo());
                }
            }
            criteria.add(Restrictions.in("cor.codigo", codigos));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        }
        if (filtro.getAcessorios() != null && !filtro.getAcessorios().isEmpty()) {
            criteria.createAlias("acessorios", "acessorio");
            List<Long> codigos = new ArrayList<Long>(0);
            for (Acessorio acessorio : filtro.getAcessorios()) {
                if (acessorio != null) {
                    codigos.add(acessorio.getCodigo());
                }
            }
            criteria.add(Restrictions.in("acessorio.codigo", codigos));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        }

    }

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

    @Transactional(readOnly = true)
    public IntencaoCompra getIntencaoCompraComCoresEAcessorios(Long codigo) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(IntencaoCompra.class);
        criteria.add(Restrictions.eq("codigo", codigo));
        IntencaoCompra intencaoCompra = (IntencaoCompra) criteria.uniqueResult();
        Hibernate.initialize(intencaoCompra.getAcessorios());
        Hibernate.initialize(intencaoCompra.getCores());
        return intencaoCompra;
    }

}