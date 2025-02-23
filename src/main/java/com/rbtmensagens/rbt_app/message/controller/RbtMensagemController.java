package com.rbtmensagens.rbt_app.message.controller;

import com.rbtmensagens.rbt_app.message.controller.dto.ChatDTO;
import com.rbtmensagens.rbt_app.message.controller.dto.MensagemDTO;
import com.rbtmensagens.rbt_app.message.model.RbtUserMen;
import com.rbtmensagens.rbt_app.message.service.RbtMenssageService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class RbtMensagemController {

    private final RbtMenssageService userdetailsservice;

    @PostMapping("/gravaMensagem")
    public ResponseEntity<Void> gravaMensagem(@RequestBody ChatDTO request) {
        if (request.getDsmensagem() != null && request.getUsername() != null) {
            userdetailsservice.gravarMensagens(request);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscaMensagem")
    public ResponseEntity<List<RbtUserMen>> buscarMensagens(@RequestParam LocalDate dtmensagem) {
        System.out.println("Data recebida: " + dtmensagem);
        return ResponseEntity.ok(userdetailsservice.buscaHistorico(dtmensagem));
    }
}

