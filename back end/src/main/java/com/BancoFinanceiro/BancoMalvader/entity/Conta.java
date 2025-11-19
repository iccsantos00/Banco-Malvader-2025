package com.BancoFinanceiro.BancoMalvader.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.BancoFinanceiro.BancoMalvader.model.enums.StatusConta;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoConta;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade abstrata que representa uma conta bancária.
 * Utiliza herança JOINED para tipos de conta.
 */
@Entity
@Table(name = "contas")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@NoArgsConstructor
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroConta;

    @Column(nullable = false, unique = true)
    private String numeroAgencia;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private TipoConta tipoConta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConta status = StatusConta.ATIVA;

    @Column(nullable = false)
    private LocalDateTime dataAbertura;

    private LocalDateTime dataEncerramento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agencia_id", nullable = false)
    private Agencia agencia;

    @OneToMany(mappedBy = "contaOrigem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        LocalDateTime agora = LocalDateTime.now();
        dataCriacao = agora;
        dataAtualizacao = agora;
        dataAbertura = agora;

        if (numeroConta == null) {
            numeroConta = gerarNumeroConta();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    protected abstract String gerarNumeroConta();

    // ============================================================================
    // Métodos de negócio
    // ============================================================================

    public void depositar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor de depósito deve ser positivo");

        saldo = saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor de saque deve ser positivo");

        if (saldo.compareTo(valor) < 0)
            throw new IllegalArgumentException("Saldo insuficiente");

        saldo = saldo.subtract(valor);
    }

    public boolean isAtiva() {
        return status == StatusConta.ATIVA;
    }

    public void transferir(Conta contaDestino, BigDecimal valor) {
        if (contaDestino == null)
            throw new IllegalArgumentException("Conta destino não pode ser nula");

        if (!contaDestino.isAtiva())
            throw new IllegalArgumentException("Conta destino não está ativa");

        this.sacar(valor);
        contaDestino.depositar(valor);
    }

    public BigDecimal consultarSaldo() {
        return saldo;
    }

    public void bloquear() {
        status = StatusConta.BLOQUEADA;
    }

    public void desbloquear() {
        status = StatusConta.ATIVA;
    }

    // Métodos protegidos para subclasses
    protected BigDecimal getSaldoConta() {
        return saldo;
    }

    protected void modificarSaldo(BigDecimal valor) {
        this.saldo = valor;
    }
}
