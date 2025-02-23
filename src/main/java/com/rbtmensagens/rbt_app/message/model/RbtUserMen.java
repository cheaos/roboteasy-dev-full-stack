package com.rbtmensagens.rbt_app.message.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "RBTUSERMEN")
public class RbtUserMen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "dsmensagem")
    private String dsmensagem;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dtmensagem")
    private LocalDateTime dtmensagem;

}
