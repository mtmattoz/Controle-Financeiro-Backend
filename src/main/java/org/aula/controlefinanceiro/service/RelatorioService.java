package org.aula.controlefinanceiro.service;

import org.aula.controlefinanceiro.model.Categoria;
import org.aula.controlefinanceiro.model.Receita;
import org.aula.controlefinanceiro.model.Transacao;
import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.repository.ReceitaRepository;
import org.aula.controlefinanceiro.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    private final TransacaoRepository transacaoRepository;
    private final ReceitaRepository receitaRepository;

    public RelatorioService(
            TransacaoRepository transacaoRepository,
            ReceitaRepository receitaRepository) {

        this.transacaoRepository = transacaoRepository;
        this.receitaRepository = receitaRepository;
    }

    public BigDecimal calcularTotalEntradas(Usuario usuario) {

        List<Receita> receitas =
                receitaRepository.findByUsuario(usuario);

        List<Transacao> transacoes =
                transacaoRepository.findByUsuarioAndTipo(usuario, "ENTRADA");

        BigDecimal totalReceitas = receitas.stream()
                .map(Receita::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalTransacoes = transacoes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalReceitas.add(totalTransacoes);
    }

    public BigDecimal calcularTotalSaidas(Usuario usuario) {

        List<Transacao> transacoes =
                transacaoRepository.findByUsuarioAndTipo(usuario, "SAIDA");

        return transacoes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularSaldo(Usuario usuario) {

        BigDecimal entradas = calcularTotalEntradas(usuario);
        BigDecimal saidas = calcularTotalSaidas(usuario);

        return entradas.subtract(saidas);
    }

    public List<Transacao> listarPorMes(
            Usuario usuario,
            int mes,
            int ano) {

        YearMonth anoMes = YearMonth.of(ano, mes);

        LocalDate inicio = anoMes.atDay(1);
        LocalDate fim = anoMes.atEndOfMonth();

        return transacaoRepository.findByUsuarioAndDataBetween(
                usuario,
                inicio,
                fim
        );
    }

    public Map<String, BigDecimal> calcularGastosPorCategoria(
            Usuario usuario) {

        List<Transacao> transacoes =
                transacaoRepository.findByUsuarioAndTipo(
                        usuario,
                        "SAIDA"
                );

        Map<String, BigDecimal> gastosPorCategoria = new HashMap<>();

        for (Transacao transacao : transacoes) {

            Categoria categoria = transacao.getCategoria();

            String nomeCategoria = categoria.getNome();

            gastosPorCategoria.merge(
                    nomeCategoria,
                    transacao.getValor(),
                    BigDecimal::add
            );
        }

        return gastosPorCategoria;
    }
}