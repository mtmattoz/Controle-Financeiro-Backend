package org.aula.controlefinanceiro.controller;

import org.aula.controlefinanceiro.model.Receita;
import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.service.ReceitaService;
import org.aula.controlefinanceiro.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/receita")
public class ReceitaController {

    private final ReceitaService receitaService;
    private final UsuarioService usuarioService;

    public ReceitaController(
            ReceitaService receitaService,
            UsuarioService usuarioService) {

        this.receitaService = receitaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar/{usuarioId}")
    public ResponseEntity<Receita> cadastrar(
            @PathVariable Long usuarioId,
            @RequestBody @Valid Receita receita) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        receita.setUsuario(usuario);

        Receita novaReceita = receitaService.cadastrar(receita);

        return ResponseEntity.ok(novaReceita);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Receita>> listarPorUsuario(
            @PathVariable Long usuarioId) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        List<Receita> receitas =
                receitaService.listarPorUsuario(usuario);

        return ResponseEntity.ok(receitas);
    }

    @DeleteMapping("/{id}/usuario/{usuarioId}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            @PathVariable Long usuarioId) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        receitaService.excluir(id, usuario);

        return ResponseEntity.noContent().build();
    }
}