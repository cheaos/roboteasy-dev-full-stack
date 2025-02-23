package com.rbtmensagens.rbt_app.message.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.rbtmensagens.rbt_app.message.controller.dto.MensagemDTO;
import com.sun.tools.jconsole.JConsoleContext;
import org.slf4j.ILoggerFactory;
import org.springframework.stereotype.Service;

import com.rbtmensagens.rbt_app.message.controller.dto.ChatDTO;
import com.rbtmensagens.rbt_app.message.exception.MessageNotFoundException;
import com.rbtmensagens.rbt_app.message.model.RbtUserMen;
import com.rbtmensagens.rbt_app.message.repository.RbtUserMenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RbtMenssageService {    	
  	  
    private final RbtUserMenRepository rbtusermenrepository;           
               
    public void gravarMensagens(ChatDTO request) {    	
    	RbtUserMen men = RbtUserMen.builder()
    					.username(request.getUsername())
    					.dsmensagem(request.getDsmensagem())
    					.dtmensagem(LocalDateTime.now())
    					.build();
    	rbtusermenrepository.save(men);    	
    }
    
    public List<RbtUserMen> buscaHistorico(LocalDate dtmensagem) {
        LocalDateTime startOfDay = dtmensagem.atStartOfDay();
        LocalDateTime endOfDay = dtmensagem.atTime(23, 59, 59);
        List<RbtUserMen> mensagem = rbtusermenrepository.buscaMensagens(startOfDay, endOfDay);
        if (mensagem == null || mensagem.isEmpty()) {  
            throw new MessageNotFoundException("Nenhuma Mensagem Neste dia!");
        }  
        return mensagem;  
    }
}