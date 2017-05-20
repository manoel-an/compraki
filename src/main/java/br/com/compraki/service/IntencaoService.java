package br.com.compraki.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.compraki.model.Grupo;
import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.model.Pessoa;
import br.com.compraki.model.Usuario;
import br.com.compraki.repository.Grupos;
import br.com.compraki.repository.IntencaoCompras;
import br.com.compraki.repository.Usuarios;

@Service
public class IntencaoService {

    private final Long[] codigos = {2l ,3l ,4l};

    @Autowired
    private Usuarios usuarios;

    @Autowired
    private Grupos grupos;

    @Autowired
    private IntencaoCompras intencaoCompras;

    @Autowired
    private UsuarioService usuarioService;

    
    //É NECESSARIO QUE O ATRIBUTO USUARIO DE INTENCAO DE COMPRAS SEJA O USUARIO LOGADO. PENSEI EM ARMAZENAR O CODIGO, PROCURANDO POR ELE AO 
    //INVES DO EMAIL.
    
    
    public List<Grupo> getGruposByRole(User user) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_CADASTRAR_USUARIO"));//ver
        if (authorized) {
            return this.grupos.findAll();
        } else {
            return this.grupos.findByCodigoIn(this.codigos);
        }
    }

   
    @Transactional  // ver porque não pode ser void
    public IntencaoCompra salvar(IntencaoCompra intencaoCompra) throws NegocioException {
    	Usuario usuario = null;//buscar em Usuarios o usuario logado passando o código dele
        try {
            if (intencaoCompra.getCodigo() != null && usuario.getCodigo() != null) {
              intencaoCompra.setDataCriacao(new Date());
              intencaoCompra.setUsuario(usuario);
            }  
              
            
            return this.intencaoCompras.save(intencaoCompra);
           
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

}//fim