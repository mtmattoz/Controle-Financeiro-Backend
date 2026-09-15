package org.aula.controlefinanceiro.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome da categoria é obrigatório.")
    @Column(nullable = false, length = 50)
    private String nome;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void aoCriar() {
        criadoEm = LocalDateTime.now();
    }
}