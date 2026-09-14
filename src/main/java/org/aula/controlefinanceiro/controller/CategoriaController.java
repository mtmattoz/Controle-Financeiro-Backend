package org.aula.controlefinanceiro.controller;

import org.aula.controlefinanceiro.model.Categoria;
import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.service.CategoriaService;
import org.aula.controlefinanceiro.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    public CategoriaController(
            CategoriaService categoriaService,
            UsuarioService usuarioService) {

        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar/{usuarioId}")
    public ResponseEntity<Categoria> cadastrar(
            @PathVariable Long usuarioId,
            @RequestBody Categoria categoria) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        categoria.setUsuario(usuario);

        Categoria novaCategoria = categoriaService.cadastrar(categoria);

        return ResponseEntity.ok(novaCategoria);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Categoria>> listarPorUsuario(
            @PathVariable Long usuarioId) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        List<Categoria> categorias =
                categoriaService.listarPorUsuario(usuario);

        return ResponseEntity.ok(categorias);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        categoriaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}