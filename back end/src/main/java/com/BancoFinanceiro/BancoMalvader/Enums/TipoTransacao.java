package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para tipos de transações
 * Classifica operações financeiras realizadas
 */
public enum TipoTransacao {
    DEPOSITO("Depósito"),
    SAQUE("Saque"),
    TRANSFERENCIA("Transferência"),
    PAGAMENTO("Pagamento"),
    INVESTIMENTO("Investimento"),
    RESGATE("Resgate"),
    TAXA("Taxa"),
    RENDIMENTO("Rendimento");

    private final String descricao;

    TipoTransacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isEntrada() {
        return this == DEPOSITO || this == RENDIMENTO || this == RESGATE;
    }

    public boolean isSaida() {
        return this == SAQUE || this == TRANSFERENCIA || this == PAGAMENTO || this == INVESTIMENTO || this == TAXA;
    }
}