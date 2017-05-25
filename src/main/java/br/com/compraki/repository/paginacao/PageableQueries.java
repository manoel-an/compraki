package br.com.compraki.repository.paginacao;

import org.hibernate.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableQueries<T, F> {

    public Page<T> filtrar(F filtro, Pageable pageable);
    
    public Long total(F filtro);
    
    public void adicionarFiltros(F filtro, Criteria criteria);

}