package com.rbtmensagens.rbt_app.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "RBTUSER")
public class RbtUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "dsnome")
    private String dsnome;

    @Column(name = "instatus")
    private Integer instatus;

    @Column(name = "cdfuncionario")
    private Integer cdfuncionario;

    @Column(name = "cdcracha")
    private Integer cdcracha;

    @Column(name = "username")
    private String username;

}
