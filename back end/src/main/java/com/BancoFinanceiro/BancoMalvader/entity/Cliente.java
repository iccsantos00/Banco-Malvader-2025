package com.BancoFinanceiro.BancoMalvader.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.BancoFinanceiro.BancoMalvader.model.enums.PerfilRisco;
import com.BancoFinanceiro.BancoMalvader.model.enums.StatusAprovacao;
import com.BancoFinanceiro.BancoMalvader.model.enums.StatusCliente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um cliente do banco
 * Herda de Usuario e adiciona atributos específicos
 */
@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario {

    @Column(nullable = false)
    private LocalDate dataNascimento;

    private String profissao;

    private Double rendaMensal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCliente status = StatusCliente.ATIVO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilRisco perfilRisco = PerfilRisco.BAIXO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAprovacao statusAprovacao = StatusAprovacao.PENDENTE;

    private String motivoReprovacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conta> contas = new ArrayList<>();

    private Integer tentativasLogin = 0;

    private LocalDateTime dataUltimoLogin;

    private LocalDateTime dataBloqueio;
}