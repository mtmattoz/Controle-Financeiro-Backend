package org.aula.controlefinanceiro.controller;

import org.aula.controlefinanceiro.model.Transacao;
import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.service.RelatorioService;
import org.aula.controlefinanceiro.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    private final RelatorioService relatorioService;
    private final UsuarioService usuarioService;

    public RelatorioController(
            RelatorioService relatorioService,
            UsuarioService usuarioService) {

        this.relatorioService = relatorioService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Map<String, BigDecimal>> gerarRelatorio(
            @PathVariable Long usuarioId) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        BigDecimal entradas =
                relatorioService.calcularTotalEntradas(usuario);

        BigDecimal saidas =
                relatorioService.calcularTotalSaidas(usuario);

        BigDecimal saldo =
                relatorioService.calcularSaldo(usuario);

        Map<String, BigDecimal> relatorio = new HashMap<>();

        relatorio.put("entradas", entradas);
        relatorio.put("saidas", saidas);
        relatorio.put("saldo", saldo);

        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/usuario/{usuarioId}/mes")
    public ResponseEntity<List<Transacao>> listarPorMes(
            @PathVariable Long usuarioId,
            @RequestParam int mes,
            @RequestParam int ano) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        List<Transacao> transacoes =
                relatorioService.listarPorMes(usuario, mes, ano);

        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/usuario/{usuarioId}/categorias")
    public ResponseEntity<Map<String, BigDecimal>> gastosPorCategoria(
            @PathVariable Long usuarioId) {

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        Map<String, BigDecimal> gastos =
                relatorioService.calcularGastosPorCategoria(usuario);

        return ResponseEntity.ok(gastos);
    }
}