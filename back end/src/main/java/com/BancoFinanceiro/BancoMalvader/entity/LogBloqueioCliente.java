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
 * Entidade para registrar logs de bloqueio de clientes
 * Auditória de ações de bloqueio e desbloqueio
 */
@Entity
@Table(name = "logs_bloqueio_cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogBloqueioCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String acao;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionarioResponsavel;

    @Column(nullable = false)
    private LocalDateTime dataAcao;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        if (dataAcao == null) {
            dataAcao = LocalDateTime.now();
        }
    }
}