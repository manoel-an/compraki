package br.com.compraki.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import br.com.compraki.enuns.EnumSexo;
import br.com.compraki.enuns.TipoPessoa;
import br.com.compraki.model.helper.PessoaHelper;
import br.com.compraki.validation.AtributoConfirmacao;

@Entity
@Table(name = "pessoa")
@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "Confirmação da senha não confere")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * O relacionamento entre pessoa e grupo se dá pelo relacionamento entre
     * Usuario e Pessoa
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Enumerated(EnumType.STRING)
    private EnumSexo sexo;

    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusao;

    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;

    @Embedded /** está embutindo na mesma tabela o endereço */
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "codigo_usuario", unique = true)
    private Usuario usuario;

    @Embedded /** está embutindo na mesma tabela o endereço */
    private Telefone telefone;

    @NotNull(message = "Tipo pessoa é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @Column(name = "apelido")
    private String apelido;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email informado é inválido")
    @Transient
    private String email;

    @Size(min = 5, message = "A senha deve ter pelo menos 5 caracteres")
    @NotBlank(message = "A senha é obrigatória")
    @Transient
    private String senha;

    @Size(min = 5, message = "A confirmação da senha deve ter pelo menos 5 caracteres")
    @NotBlank(message = "A confirmação de senha é obrigatória")
    @Transient
    private String confirmacaoSenha;

    @NotBlank(message = "O Nome/Razão Social é obrigatório")
    private String nome;

    @NotNull(message = "A data de Nascimento é obrigatória")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @NotBlank(message = "O Nome Fantasia é obrigatório")
    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Column(name = "cpf_cnpj")
    private String cpfOuCnpj;

    @Transient
    private PessoaHelper pessoaHelper;

    // @PrePersist
    // @PreUpdate
    // private void prePersistPreUpdate() {
    // if (this.nomeFantasia != null && this.nomeFantasia.equals("-")) {
    // this.nomeFantasia = null;
    // }
    // if (getPessoaHelper().getInputTipoPessoa().equals("inputPessoaJURIDICA"))
    // {
    // this.dataNascimento = null;
    // }
    // }

    // Getters and Setters
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EnumSexo getSexo() {
        if (sexo == null) {
            sexo = EnumSexo.FEMININO;
        }
        return sexo;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public PessoaHelper getPessoaHelper() {
        if (pessoaHelper == null) {
            pessoaHelper = new PessoaHelper();
        }
        return pessoaHelper;
    }

    public void setPessoaHelper(PessoaHelper pessoaHelper) {
        this.pessoaHelper = pessoaHelper;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pessoa other = (Pessoa) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }

}
