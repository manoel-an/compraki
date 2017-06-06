package br.com.compraki.repository.helper;

import org.hibernate.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.compraki.model.Usuario;
import br.com.compraki.model.veiculo.Carro;
import br.com.compraki.repository.filter.CarroFilter;

public interface CarrosQueries {

    public Page<Carro> filtrar(Usuario usuario, CarroFilter filtro, Pageable pageable);

    public Long total(CarroFilter filtro);

    public void adicionarFiltros(Usuario usuario, CarroFilter filtro, Criteria criteria);

    public Carro getFullCar(Long codigo);

    public Carro getCarWithAcessories(Long codigo);

}