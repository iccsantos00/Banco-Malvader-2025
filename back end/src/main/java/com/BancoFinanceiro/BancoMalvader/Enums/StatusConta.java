package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para status de contas bancárias
 * Controla estado operacional das contas
 */
public enum StatusConta {
    ATIVA("Ativa", true),
    BLOQUEADA("Bloqueada", false),
    ENCERRADA("Encerrada", false),
    PENDENTE_APROVACAO("Pendente de Aprovação", false);

    private final String descricao;
    private final boolean operacional;

    StatusConta(String descricao, boolean operacional) {
        this.descricao = descricao;
        this.operacional = operacional;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isOperacional() {
        return operacional;
    }
}