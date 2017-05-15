package br.com.compraki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.compraki.model.Usuario;
import br.com.compraki.repository.helper.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

    @Query("SELECT u FROM Usuario u INNER JOIN FETCH u.grupos WHERE u.email = (:email)")
    public Optional<Usuario> findByEmailAndFetchEager(@Param("email") String email);

    public Optional<Usuario> findByEmail(String email);

}