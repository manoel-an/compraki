package br.com.compraki.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import br.com.compraki.enuns.TipoPessoa;
import br.com.compraki.model.Grupo;
import br.com.compraki.model.Pessoa;
import br.com.compraki.model.Telefone;
import br.com.compraki.model.Usuario;
import br.com.compraki.repository.Grupos;
import br.com.compraki.repository.Pessoas;
import br.com.compraki.repository.Usuarios;

@Service
public class PessoaService {

	private final Long[] codigos = { 2l, 3l, 4l };

	@Autowired
	private Usuarios usuarios;

	@Autowired
	private Grupos grupos;

	@Autowired
	private Pessoas pessoas;

	@Autowired
	private UsuarioService usuarioService;

	public List<Grupo> getGruposByRole(User user) {
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_CADASTRAR_USUARIO"));
		if (authorized) {
			return this.grupos.findAll();
		} else {
			return this.grupos.findByCodigoIn(this.codigos);
		}
	}

	public void getFieldError(Pessoa pessoa, BindingResult result) {
		if (result.getFieldError("nome") != null
				&& pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaFISICA")) {
			pessoa.getPessoaHelper().setErroNome(Boolean.TRUE);
		}
		if (result.getFieldError("nome") != null
				&& pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaJURIDICA")) {
			pessoa.getPessoaHelper().setErroRazaoSocial(Boolean.TRUE);
		}
		if (result.getFieldError("dataNascimento") != null) {
			pessoa.getPessoaHelper().setErroDataNascimento(Boolean.TRUE);
		}
		if (result.getFieldError("cpfOuCnpj") != null
				&& pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaFISICA")) {
			pessoa.getPessoaHelper().setErroCpf(Boolean.TRUE);
		}
		if (result.getFieldError("cpfOuCnpj") != null
				&& pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaJURIDICA")) {
			pessoa.getPessoaHelper().setErroCnpj(Boolean.TRUE);
		}
		if (result.getFieldError("nomeFantasia") != null
				&& pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaJURIDICA")) {
			pessoa.getPessoaHelper().setErroNomeFantasia(Boolean.TRUE);
		}
		this.carregaCamposTipoPessoa(pessoa, result, Boolean.FALSE);
	}

	public void carregaCamposTipoPessoa(Pessoa pessoa, BindingResult result, boolean pessoaSalva) {
		if (pessoaSalva) {
			if (pessoa.getTipoPessoa().equals(TipoPessoa.FISICA)) {
				pessoa.getPessoaHelper().setNome(pessoa.getNome());
				pessoa.getPessoaHelper().setApelido(pessoa.getApelido());
				pessoa.getPessoaHelper().setSexo(pessoa.getSexo());
				pessoa.getPessoaHelper().setCpf(pessoa.getCpfOuCnpj());
				pessoa.getPessoaHelper().setDataNascimento(pessoa.getDataNascimento());
				pessoa.getPessoaHelper().setInputTipoPessoa("inputPessoaFISICA");
			} else {
				pessoa.getPessoaHelper().setRazaoSocial(pessoa.getNome());
				pessoa.getPessoaHelper().setApelido(pessoa.getApelido());
				pessoa.getPessoaHelper().setCnpj(pessoa.getCpfOuCnpj());
				pessoa.getPessoaHelper().setNomeFantasia(pessoa.getNomeFantasia());
				pessoa.getPessoaHelper().setInputTipoPessoa("inputPessoaJURIDICA");
			}
			pessoa.setEmail(pessoa.getUsuario().getEmail());
		} else {
			if (pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaFISICA")) {
				pessoa.getPessoaHelper().setNome(pessoa.getNome());
				pessoa.getPessoaHelper().setApelido(pessoa.getApelido());
				pessoa.getPessoaHelper().setSexo(pessoa.getSexo());
			}
			if (pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaJURIDICA")) {
				pessoa.getPessoaHelper().setRazaoSocial(pessoa.getNome());
				pessoa.getPessoaHelper().setApelido(pessoa.getApelido());
			}
			if (result.getFieldError("dataNascimento") == null) {
				pessoa.getPessoaHelper().setDataNascimento(pessoa.getDataNascimento());
			}
			if (result.getFieldError("cpfOuCnpj") == null
					&& pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaFISICA")) {
				pessoa.getPessoaHelper().setCpf(pessoa.getCpfOuCnpj());
			}
			if (result.getFieldError("cpfOuCnpj") == null
					&& pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaJURIDICA")) {
				pessoa.getPessoaHelper().setCnpj(pessoa.getCpfOuCnpj());
			}
			if (result.getFieldError("nomeFantasia") == null) {
				pessoa.getPessoaHelper().setNomeFantasia(pessoa.getNomeFantasia());
			}
		}
	}

	public Pessoa getPessoaByUsuarioLogado(Usuario usuario) {
		Optional<Pessoa> op = this.pessoas.findByUsuario(usuario);
		if (op.isPresent()) {
			Pessoa pessoa = op.get();
			pessoa.setUsuario(this.usuarios.findByEmailAndFetchEager(usuario.getEmail()).get());
			pessoa.setEmail(usuario.getEmail());
			pessoa.setSenha(usuario.getSenha());
			pessoa.setConfirmacaoSenha(usuario.getSenha());
			this.carregaCamposTipoPessoa(pessoa, null, Boolean.TRUE);
			return pessoa;
		} else {
			return new Pessoa();
		}
	}

	@Transactional
	public void salvarPessoa(Pessoa pessoa) throws NegocioException {
		try {
			Optional<Pessoa> op = this.pessoas.findByCpfOuCnpj(pessoa.getCpfOuCnpj());
			Pessoa p = null;
			if (op.isPresent()) {
				p = op.get();
			}
			if (p != null && pessoa.getCodigo() != p.getCodigo()) {
				throw new NegocioException("CPF/CNPJ já cadastrado");
			} else {
				if (pessoa.getCodigo() == null) {
					pessoa.setDataInclusao(LocalDateTime.now());
				}
				pessoa.setDataAlteracao(LocalDateTime.now());
				pessoa.getUsuario().setEmail(pessoa.getEmail());
				pessoa.getUsuario().setSenha(pessoa.getSenha());
				Usuario usuario = this.usuarioService.salvarUsuario(pessoa.getUsuario());
				pessoa.setUsuario(usuario);
				this.pessoas.save(pessoa);
			}
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Transactional
	public Telefone atualizarTelefonePessoa(Telefone telefone) throws NegocioException {
		try {
			Pessoa pessoa = this.pessoas.findByCodigo(telefone.getCodigoPessoa()).get();
			pessoa.getTelefone().setNumeroUm(telefone.getNumeroUm());
			pessoa = this.pessoas.saveAndFlush(pessoa);
			return pessoa.getTelefone();
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

}