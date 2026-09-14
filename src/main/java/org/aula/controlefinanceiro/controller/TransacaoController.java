package org.aula.controlefinanceiro.controller;

import org.aula.controlefinanceiro.model.Categoria;
import org.aula.controlefinanceiro.model.Transacao;
import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.service.CategoriaService;
import org.aula.controlefinanceiro.service.TransacaoService;
import org.aula.controlefinanceiro.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    public TransacaoController(
            TransacaoService transacaoService,
            UsuarioService usuarioService,
            CategoriaService categoriaService) {

        this.transacaoService = transacaoService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }

    @PostMapping("/cadastrar/{usuarioId}/{categoriaId}")
    public ResponseEntity<Transacao> cadastrar(
            @PathVariable Long usuarioId,
            @PathVariable Long categoriaId,
            @RequestBody Transacao transacao) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        transacao.setUsuario(usuario);
        transacao.setCategoria(categoria);

        Transacao novaTransacao =
                transacaoService.cadastrar(transacao);

        return ResponseEntity.ok(novaTransacao);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Transacao>> listarPorUsuario(
            @PathVariable Long usuarioId) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        List<Transacao> transacoes =
                transacaoService.listarPorUsuario(usuario);

        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/usuario/{usuarioId}/categoria/{categoriaId}")
    public ResponseEntity<List<Transacao>> listarPorCategoria(
            @PathVariable Long usuarioId,
            @PathVariable Long categoriaId) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        List<Transacao> transacoes =
                transacaoService.listarPorCategoria(usuario, categoria);

        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/usuario/{usuarioId}/tipo/{tipo}")
    public ResponseEntity<List<Transacao>> listarPorTipo(
            @PathVariable Long usuarioId,
            @PathVariable String tipo) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        List<Transacao> transacoes =
                transacaoService.listarPorTipo(usuario, tipo);

        return ResponseEntity.ok(transacoes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        transacaoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}