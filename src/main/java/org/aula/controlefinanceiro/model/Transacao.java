package org.aula.controlefinanceiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição da transação é obrigatória.")
    @Column(nullable = false, length = 150)
    private String descricao;

    @NotNull(message = "Valor da transação é obrigatório.")
    @Positive(message = "O valor da transação deve ser maior que zero.")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @NotBlank(message = "Tipo da transação é obrigatório.")
    @Pattern(
            regexp = "ENTRADA|SAIDA",
            message = "O tipo deve ser ENTRADA ou SAIDA."
    )
    @Column(nullable = false, length = 10)
    private String tipo;

    @NotNull(message = "Data da transação é obrigatória.")
    @Column(nullable = false)
    private LocalDate data;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void aoCriar() {
        criadoEm = LocalDateTime.now();
    }
}