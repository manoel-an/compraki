package br.com.compraki.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import br.com.compraki.model.Grupo;
import br.com.compraki.repository.Grupos;

@Service
public class PessoaService {

    private final Long[] codigos = {2l ,3l ,4l};

    @Autowired
    private Grupos grupos;

    public List<Grupo> getGruposByRole(User user) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_CADASTRAR_USUARIO"));
        if (authorized) {
            return this.grupos.findAll();
        } else {
            return this.grupos.findByCodigoIn(this.codigos);
        }

    }

}