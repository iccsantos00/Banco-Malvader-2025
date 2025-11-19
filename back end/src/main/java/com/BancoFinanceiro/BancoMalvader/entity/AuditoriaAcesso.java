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
 * Entidade para auditoria de acessos ao sistema
 * Registra tentativas de login e acessos
 */
@Entity
@Table(name = "auditoria_acessos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private String emailTentativa;

    @Column(nullable = false)
    private String enderecoIp;

    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false)
    private Boolean sucesso;

    private String motivoFalha;

    @Column(nullable = false)
    private LocalDateTime dataAcesso;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        if (dataAcesso == null) {
            dataAcesso = LocalDateTime.now();
        }
    }
}