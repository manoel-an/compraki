package br.com.compraki.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.compraki.model.IntencaoCompra;
import br.com.compraki.repository.IntencaoCompras;

@Service
public class IntencaoService {

    @Autowired
    private IntencaoCompras intencaoCompras;
    
    /*
    public List<Grupo> getGruposByRole(User user) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_CADASTRAR_USUARIO"));//ver
        if (authorized) {
            return this.grupos.findAll();
        } else {
            return this.grupos.findByCodigoIn(this.codigos);
        }
    }*/

   
    @Transactional 
    public void salvar(IntencaoCompra intencaoCompra) throws NegocioException {
    	this.intencaoCompras.save(intencaoCompra);
    	/*
        try {
            if (intencaoCompra != null && intencaoCompra.getUsuario() != null) {
                intencaoCompra.setDataCriacao(new Date());
            	this.intencaoCompras.save(intencaoCompra);
            	System.out.println("");
            }	
    		} catch (Exception e) {
    			throw new NegocioException(e.getMessage());
    		}*/
        
    }

}//fim