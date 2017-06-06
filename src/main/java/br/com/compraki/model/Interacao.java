package br.com.compraki.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "interacao")
public class Interacao implements Serializable {

    private static final long serialVersionUID = -8354269525264410932L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    // @Enumerated(EnumType.STRING)
    // private EnumSexo sexo;

}
