package com.rbtmensagens.rbt_app.message.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rbtmensagens.rbt_app.message.model.RbtUserMen;


@Repository
public interface RbtUserMenRepository extends JpaRepository<RbtUserMen, Integer>{
		
	@Query("SELECT a FROM RbtUserMen a WHERE a.dtmensagem BETWEEN :startOfDay AND :endOfDay")
	List<RbtUserMen> buscaMensagens(@Param("startOfDay") LocalDateTime startOfDay,
	                                @Param("endOfDay") LocalDateTime endOfDay);	
}
