package org.aula.controlefinanceiro.service;

import org.aula.controlefinanceiro.model.Usuario;
import org.aula.controlefinanceiro.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.aula.controlefinanceiro.exception.SenhaIncorretaException;
import org.aula.controlefinanceiro.exception.UsuarioNaoEncontradoException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrar(Usuario usuario) {

        if (usuarioRepository.existsByNomeUsuario(usuario.getNomeUsuario())) {
            throw new RuntimeException("Nome de usuário já está em uso.");
        }

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        return usuarioRepository.save(usuario);
    }

    public Usuario login(String nomeUsuario, String senha) {

        Usuario usuario = usuarioRepository
                .findByNomeUsuario(nomeUsuario)
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Usuário não encontrado.")
                );

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new SenhaIncorretaException("Senha incorreta.");
        }

        return usuario;
    }

    public Usuario buscarPorId(Long id) {

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado.")
                );
    }
}