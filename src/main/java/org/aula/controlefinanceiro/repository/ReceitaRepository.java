package org.aula.controlefinanceiro.repository;

import org.aula.controlefinanceiro.model.Receita;
import org.aula.controlefinanceiro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByUsuario(Usuario usuario);

    List<Receita> findByUsuarioAndDataBetween(
            Usuario usuario,
            LocalDate inicio,
            LocalDate fim
    );
}