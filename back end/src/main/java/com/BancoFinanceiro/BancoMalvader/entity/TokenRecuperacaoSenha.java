package com.BancoFinanceiro.BancoMalvader.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade para tokens de recuperação de senha
 * Controla validade e uso de tokens para redefinição
 */
@Entity
@Table(name = "tokens_recuperacao_senha")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenRecuperacaoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime dataExpiracao;

    @Column(nullable = false)
    private Boolean utilizado = false;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public boolean isExpirado() {
        return LocalDateTime.now().isAfter(dataExpiracao);
    }

    public boolean isValido() {
        return !utilizado && !isExpirado();
    }
}