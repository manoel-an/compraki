package br.com.compraki.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.compraki.model.Grupo;
import br.com.compraki.model.Pessoa;
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

        if (pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaFISICA")) {
            pessoa.getPessoaHelper().setNome(pessoa.getNome());
            pessoa.getPessoaHelper().setApelido(pessoa.getApelido());
            pessoa.getPessoaHelper().setSexo(pessoa.getSexo());
        }
        if (pessoa.getPessoaHelper().getInputTipoPessoa().equals("inputPessoaJURIDICA")) {
            pessoa.getPessoaHelper().setRazaoSocial(pessoa.getNome());
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