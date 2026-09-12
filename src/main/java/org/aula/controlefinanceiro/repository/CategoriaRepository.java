package org.aula.controlefinanceiro.repository;

import org.aula.controlefinanceiro.model.Categoria;
import org.aula.controlefinanceiro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByUsuario(Usuario usuario);
}