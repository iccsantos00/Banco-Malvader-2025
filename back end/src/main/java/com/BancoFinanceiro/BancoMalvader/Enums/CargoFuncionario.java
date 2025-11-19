package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para cargos de funcionários
 * Define hierarquia e responsabilidades
 */
public enum CargoFuncionario {
    GERENTE("Gerente"),
    SUPERVISOR("Supervisor"),
    ANALISTA("Analista"),
    ASSISTENTE("Assistente"),
    ATENDENTE("Atendente");

    private final String descricao;

    CargoFuncionario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isGerente() {
        return this == GERENTE;
    }

    public boolean podeAprovarCadastros() {
        return this == GERENTE || this == SUPERVISOR;
    }

    public boolean podeAcessarRelatorios() {
        return this == GERENTE || this == SUPERVISOR || this == ANALISTA;
    }
}