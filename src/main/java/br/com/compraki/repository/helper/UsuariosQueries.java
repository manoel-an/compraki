package br.com.compraki.repository.helper;

import java.util.List;
import java.util.Optional;

import br.com.compraki.model.Usuario;

public interface UsuariosQueries {

    public Optional<Usuario> porEmailEAtivo(String email);

    public List<String> permissoes(Usuario usuario);

}