package br.com.compraki.repository.helper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
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

import br.com.compraki.model.veiculo.Acessorio;
import br.com.compraki.repository.filter.AcessorioFilter;
import br.com.compraki.repository.paginacao.PaginacaoUtil;

public class AcessoriosImpl implements AcessoriosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Acessorio> filtrar(AcessorioFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Acessorio.class);
		adicionarFiltros(filtro, criteria);
		paginacaoUtil.preparar(criteria, pageable);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	@Override
	public Long total(AcessorioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Acessorio.class);
		this.adicionarFiltros(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	@Override
	public void adicionarFiltros(AcessorioFilter filtro, Criteria criteria) {
		if (!StringUtils.isEmpty(filtro.getCodigo())) {
			criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
		}
		if (!StringUtils.isEmpty(filtro.getTipoVeiculo())) {
			criteria.add(Restrictions.eq("tipoVeiculo", filtro.getTipoVeiculo()));
		}
		if (!StringUtils.isEmpty(filtro.getAcessorio())) {
			criteria.add(Restrictions.ilike("descricao", filtro.getAcessorio(), MatchMode.ANYWHERE));
		}
	}
}