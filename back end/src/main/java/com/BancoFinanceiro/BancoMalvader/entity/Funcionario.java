package com.BancoFinanceiro.BancoMalvader.model.entity;

import java.time.LocalDateTime;
import com.BancoFinanceiro.BancoMalvader.model.enums.CargoFuncionario;
import com.BancoFinanceiro.BancoMalvader.model.enums.PerfilAcesso;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um funcionário do banco
 * Herda de Usuario com atributos específicos de funcionário
 */
@Entity
@Table(name = "funcionarios")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario extends Usuario {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoFuncionario cargo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilAcesso perfilAcesso;

    @Column(nullable = false)
    private String matricula;

    private LocalDateTime dataAdmissao;

    private LocalDateTime dataDemissao;

    private Boolean ativo = true;
}