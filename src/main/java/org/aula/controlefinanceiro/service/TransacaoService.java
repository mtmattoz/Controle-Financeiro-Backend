package org.aula.controlefinanceiro.service;

import org.aula.controlefinanceiro.model.Transacao;
import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.aula.controlefinanceiro.exception.AcessoNegadoException;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public Transacao cadastrar(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public List<Transacao> listarPorUsuario(Usuario usuario) {
        return transacaoRepository.findByUsuario(usuario);
    }

    public List<Transacao> listarPorCategoria(
            Usuario usuario,
            org.aula.controlefinanceiro.model.Categoria categoria) {

        return transacaoRepository.findByUsuarioAndCategoria(usuario, categoria);
    }

    public List<Transacao> listarPorTipo(
            Usuario usuario,
            String tipo) {

        return transacaoRepository.findByUsuarioAndTipo(usuario, tipo);
    }

    public void excluir(Long id, Usuario usuario) {

        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transação não encontrada.")
                );

        if (!transacao.getUsuario().getId().equals(usuario.getId())) {
            throw new AcessoNegadoException(
                    "Você não pode excluir uma transação de outro usuário."
            );
        }

        transacaoRepository.delete(transacao);
    }
}