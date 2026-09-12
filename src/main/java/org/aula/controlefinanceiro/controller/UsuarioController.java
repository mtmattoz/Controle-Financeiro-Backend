package org.aula.controlefinanceiro.controller;

import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.cadastrar(usuario);

        return ResponseEntity.ok(novoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(
            @RequestParam String nomeUsuario,
            @RequestParam String senha) {

        Usuario usuario = usuarioService.login(nomeUsuario, senha);

        return ResponseEntity.ok(usuario);
    }
}