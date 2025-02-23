package com.rbtmensagens.rbt_app.users.controller;

import com.rbtmensagens.rbt_app.users.controller.dto.CreateUserDTO;
import com.rbtmensagens.rbt_app.users.controller.dto.UserDTO;
import com.rbtmensagens.rbt_app.users.service.RbtUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final RbtUserService userdetailsservice;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO userdto) {
        final String token = userdetailsservice.loaduserByRbt(userdto);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Void> cadastro(@RequestBody CreateUserDTO request) {
        userdetailsservice.cadastrarUsuario(request);
        return ResponseEntity.ok().build();

    }
}

