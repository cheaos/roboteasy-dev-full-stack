package com.rbtmensagens.rbt_app.users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rbtmensagens.rbt_app.users.model.RbtUser;

@Repository
public interface RbtUserRepository extends JpaRepository<RbtUser, Integer>{
	
	public List<RbtUser> findByInstatus(Integer instatus);
	
	@Query(value="SELECT a FROM RbtUser a"+
			  " WHERE a.username = :username"+
			  " and a.password = :password"  +
			  " and a.cdfuncionario = :cdfuncionario" +
			  " and a.cdcracha = :cdcracha")				  	
	public RbtUser existeUsuario(@Param("username") String username,
							 @Param("password") String password,
							 @Param("cdfuncionario") Integer cdfuncionario,
							 @Param("cdcracha") Integer cdcracha);		
	
	@Query(value="SELECT COALESCE(MAX(id),0) as id FROM RbtUser"+
				  " WHERE cdcracha = :cdcracha")				  	
	public List<RbtUser> buscaId(@Param("cdcracha") Integer cdcracha);
	
	@Query(value="SELECT a FROM RbtUser a"+
				  " WHERE a.username = :username"+
				  " and a.password = :password")				  	
	public RbtUser buscaUsuarioLogin(@Param("username") String username,
								     @Param("password") String password);
	
	@Query(value="SELECT a FROM RbtUser a"+
			  " WHERE a.username = :username")			  
	public RbtUser buscaUsuario(@Param("username") String username);										  	
}
