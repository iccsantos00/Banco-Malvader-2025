package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para tipos de contas bancárias
 * Define características específicas de cada tipo
 */
public enum TipoConta {
    CORRENTE("Conta Corrente", true, false),
    POUPANCA("Conta Poupança", false, true),
    INVESTIMENTO("Conta Investimento", false, true);

    private final String descricao;
    private final boolean permiteChequeEspecial;
    private final boolean possuiRendimento;

    TipoConta(String descricao, boolean permiteChequeEspecial, boolean possuiRendimento) {
        this.descricao = descricao;
        this.permiteChequeEspecial = permiteChequeEspecial;
        this.possuiRendimento = possuiRendimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isPermiteChequeEspecial() {
        return permiteChequeEspecial;
    }

    public boolean isPossuiRendimento() {
        return possuiRendimento;
    }

    public double getTaxaManutencao() {
        switch (this) {
            case CORRENTE: return 15.00;
            case POUPANCA: return 0.00;
            case INVESTIMENTO: return 10.00;
            default: return 0.00;
        }
    }
}