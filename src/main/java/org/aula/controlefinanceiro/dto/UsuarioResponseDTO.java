package org.aula.controlefinanceiro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nomeUsuario;
    private LocalDateTime criadoEm;
}