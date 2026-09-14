package org.aula.controlefinanceiro.service;

import org.aula.controlefinanceiro.model.Categoria;
import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria cadastrar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarPorUsuario(Usuario usuario) {
        return categoriaRepository.findByUsuario(usuario);
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
    }

    public void excluir(Long id) {
        categoriaRepository.deleteById(id);
    }
}