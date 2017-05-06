package br.com.compraki.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.compraki.model.Usuario;
import br.com.compraki.service.NegocioException;
import br.com.compraki.service.UsuarioService;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private HttpServletRequest req;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = this.usuarioService.getUsuarios().findByEmail(email);
		Usuario usuario = null;
		if (req.getParameter("novo_cadastro") != null && !usuarioOptional.isPresent()) {
			usuario = this.validaUsuario();
		}
		if (req.getParameter("novo_cadastro") != null && usuarioOptional.isPresent()
				&& req.getParameter("password").length() < 5) {
			throw new NegocioException("Senha tem que ter pelo menos 5 caracteres", 3, req.getParameter("username"));
		} else if (req.getParameter("novo_cadastro") != null && usuarioOptional.isPresent()
				&& req.getParameter("confirm_password").length() != req.getParameter("password").length()) {
			throw new NegocioException("Confirmação da senha não confere", 4, req.getParameter("username"));
		} else {
			if (usuarioOptional.isPresent() || usuario != null) {
				if (usuario == null) {
					usuario = usuarioOptional.get();
				}
				return new UsuarioSistema(usuario, getPermissoes(usuario));
			} else {
				usuario = usuarioOptional
						.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorretos"));
				return new UsuarioSistema(usuario, getPermissoes(usuario));
			}
		}
	}

	private Usuario validaUsuario() {
		if (req.getParameter("username").isEmpty()) {
			throw new NegocioException("O email é obrigatório", 1);
		}
		if (!req.getParameter("username").isEmpty()) {
			Matcher matcher = pattern.matcher(req.getParameter("username"));
			if (!matcher.matches()) {
				throw new NegocioException("O email é inválido", 2, req.getParameter("username"));
			}
		}
		if (req.getParameter("password").length() < 5) {
			throw new NegocioException("Senha tem que ter pelo menos 5 caracteres", 3, req.getParameter("username"));
		}
		if (req.getParameter("confirm_password").length() != req.getParameter("password").length()) {
			throw new NegocioException("Confirmação da senha não confere", 4, req.getParameter("username"));
		}
		Usuario novoUsuario = new Usuario();
		novoUsuario.setAtivo(Boolean.TRUE);
		novoUsuario.setEmail(req.getParameter("username"));
		novoUsuario.setSenha(req.getParameter("password"));
		novoUsuario.setNome("Novo Usuário");
		novoUsuario.setGrupos(null);
		return this.usuarioService.salvarPreCadastro(novoUsuario);
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>(0);

		List<String> permissoes = this.usuarioService.getUsuarios().permissoes(usuario);

		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));

		return authorities;
	}

}
