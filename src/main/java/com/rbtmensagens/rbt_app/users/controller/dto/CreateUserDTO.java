package com.rbtmensagens.rbt_app.users.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
	private Integer cdfuncionario;
	private Integer cdcracha;
	private String username;
	private String password;
	private String dsnome;
	private Integer instatus;
}
