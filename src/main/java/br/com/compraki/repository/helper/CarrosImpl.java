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

import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Carro;
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
	public Page<Carro> filtrar(Usuario usuario, CarroFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Carro.class);
		criteria.createAlias("modelo", "modelo"); // inner join by default
		criteria.createAlias("modelo.fabricante", "fabricante");
		criteria.createAlias("cor", "cor");
		adicionarFiltros(usuario, filtro, criteria);
		paginacaoUtil.preparar(criteria, pageable);
		return new PageImpl<>(criteria.list(), pageable, total(filtro, usuario));
	}

	@Override
	public Long total(CarroFilter filtro, Usuario usuario) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Carro.class);
		this.adicionarFiltros(usuario, filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	@Override
	public void adicionarFiltros(Usuario usuario, CarroFilter filtro, Criteria criteria) {
		criteria.add(Restrictions.eq("usuario", usuario));
		if (!StringUtils.isEmpty(filtro.getCodigo())) {
			criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
		}
		if (!StringUtils.isEmpty(filtro.getModelo())) {
			criteria.add(Restrictions.ilike("modelo.descricao", filtro.getModelo(), MatchMode.ANYWHERE));
		}
		if (!StringUtils.isEmpty(filtro.getFabricante())) {
			criteria.add(Restrictions.eq("modelo.fabricante", filtro.getFabricante()));
		}
		if (!StringUtils.isEmpty(filtro.getTipoVeiculo())) {
			criteria.add(Restrictions.eq("tipoVeiculo", filtro.getTipoVeiculo()));
		}
		if (!StringUtils.isEmpty(filtro.getCor())) {
			criteria.add(Restrictions.eq("cor", filtro.getCor()));
		}
	}

	@Transactional(readOnly = true)
	public Carro getFullCar(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Carro.class);
		criteria.createAlias("modelo", "modelo");
		criteria.createAlias("modelo.fabricante", "fabricante");
		criteria.createAlias("cor", "cor");
		criteria.add(Restrictions.eq("codigo", codigo));
		Carro carro = (Carro) criteria.uniqueResult();
		Hibernate.initialize(carro.getAcessorios());
		return carro;
	}

	@Transactional(readOnly = true)
	public Carro getCarWithAcessories(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Carro.class);
		criteria.add(Restrictions.eq("codigo", codigo));
		Carro carro = (Carro) criteria.uniqueResult();
		Hibernate.initialize(carro.getAcessorios());
		return carro;
	}

}