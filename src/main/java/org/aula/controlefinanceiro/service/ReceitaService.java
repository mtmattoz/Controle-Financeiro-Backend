package org.aula.controlefinanceiro.service;

import org.aula.controlefinanceiro.model.Receita;
import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.repository.ReceitaRepository;
import org.springframework.stereotype.Service;
import org.aula.controlefinanceiro.exception.AcessoNegadoException;
import java.util.List;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    public Receita cadastrar(Receita receita) {
        return receitaRepository.save(receita);
    }

    public List<Receita> listarPorUsuario(Usuario usuario) {
        return receitaRepository.findByUsuario(usuario);
    }

    public void excluir(Long id, Usuario usuario) {

        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Receita não encontrada.")
                );

        if (!receita.getUsuario().getId().equals(usuario.getId())) {
            throw new AcessoNegadoException(
                    "Você não pode excluir uma receita de outro usuário."
            );
        }

        receitaRepository.delete(receita);
    }
}