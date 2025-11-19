package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para status de aprovação
 * Controla fluxo de aprovação de cadastros
 */
public enum StatusAprovacao {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    EM_ANALISE("Em Análise");

    private final String descricao;

    StatusAprovacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isAprovado() {
        return this == APROVADO;
    }

    public boolean isReprovado() {
        return this == REPROVADO;
    }

    public boolean isPendente() {
        return this == PENDENTE || this == EM_ANALISE;
    }
}