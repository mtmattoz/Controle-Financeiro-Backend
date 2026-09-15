package org.aula.controlefinanceiro.controller;

import org.aula.controlefinanceiro.dto.UsuarioResponseDTO;
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
    public ResponseEntity<UsuarioResponseDTO> cadastrar(
            @RequestBody Usuario usuario) {

        Usuario novoUsuario = usuarioService.cadastrar(usuario);

        UsuarioResponseDTO resposta = new UsuarioResponseDTO(
                novoUsuario.getId(),
                novoUsuario.getNomeUsuario(),
                novoUsuario.getCriadoEm()
        );

        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(
            @RequestParam String nomeUsuario,
            @RequestParam String senha) {

        Usuario usuario = usuarioService.login(nomeUsuario, senha);

        UsuarioResponseDTO resposta = new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getCriadoEm()
        );

        return ResponseEntity.ok(resposta);
    }
}