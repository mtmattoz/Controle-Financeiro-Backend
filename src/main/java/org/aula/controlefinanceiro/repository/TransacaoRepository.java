package org.aula.controlefinanceiro.repository;

import org.aula.controlefinanceiro.model.Transacao;
import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByUsuario(Usuario usuario);

    List<Transacao> findByUsuarioAndCategoria(Usuario usuario, Categoria categoria);

    List<Transacao> findByUsuarioAndTipo(Usuario usuario, String tipo);
}