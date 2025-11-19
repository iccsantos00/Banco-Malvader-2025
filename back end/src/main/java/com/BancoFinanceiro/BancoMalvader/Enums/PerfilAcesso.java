package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para perfis de acesso de funcionários
 * Define níveis de autorização no sistema
 */
public enum PerfilAcesso {
    TOTAL("Acesso Total"),
    GERENCIAL("Acesso Gerencial"),
    OPERACIONAL("Acesso Operacional"),
    CONSULTA("Acesso Somente Consulta");

    private final String descricao;

    PerfilAcesso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean podeAlterarDados() {
        return this == TOTAL || this == GERENCIAL;
    }

    public boolean podeAprovarOperacoes() {
        return this == TOTAL || this == GERENCIAL;
    }

    public boolean podeEmitirRelatorios() {
        return this != CONSULTA;
    }
}