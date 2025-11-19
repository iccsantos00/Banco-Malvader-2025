package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para status de clientes
 * Controla estado do cliente no sistema
 */
public enum StatusCliente {
    ATIVO("Ativo"),
    INATIVO("Inativo"),
    BLOQUEADO("Bloqueado"),
    PENDENTE("Pendente"),
    SUSPENSO("Suspenso");

    private final String descricao;

    StatusCliente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isAtivo() {
        return this == ATIVO;
    }

    public boolean podeRealizarTransacoes() {
        return this == ATIVO;
    }
}