package br.com.compraki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compraki.model.Pessoa;
import br.com.compraki.model.Usuario;

public interface Pessoas extends JpaRepository<Pessoa, Long> {

	public Optional<Pessoa> findByUsuario(Usuario usuario);

	public Optional<Pessoa> findByCpfOuCnpj(String cpfOuCnpj);

}