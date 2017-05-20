package br.com.compraki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.compraki.model.Usuario;
import br.com.compraki.repository.Grupos;
import br.com.compraki.repository.Usuarios;

@Service
public class UsuarioService {

	@Autowired
	private Usuarios usuarios;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Grupos grupos;

	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		usuario.setAtivo(usuario.getAtivo());
		return usuarios.saveAndFlush(usuario);
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public Grupos getGrupos() {
		return grupos;
	}

}