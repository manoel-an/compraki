package br.com.compraki.repository.helper;

import org.hibernate.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Usuario;
import br.com.compraki.repository.filter.IntencaoFilter;

public interface IntencoesQueries {

    public Page<IntencaoCompra> filtrar(Usuario usuario, IntencaoFilter filtro, Pageable pageable);

    public Long total(IntencaoFilter filtro);

    public void adicionarFiltros(Usuario usuario, IntencaoFilter filtro, Criteria criteria);

    public IntencaoCompra getIntencaoCompraCompleto(Long codigo);

    public IntencaoCompra getIntencaoCompraComCoresEAcessorios(Long codigo);
}