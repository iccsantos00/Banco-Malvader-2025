package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para perfis de risco de clientes
 * Define tolerância a risco para investimentos
 */
public enum PerfilRisco {
    CONSERVADOR("Conservador"),
    MODERADO("Moderado"),
    ARROJADO("Arrojado"),
    BAIXO("Baixo"),
    MEDIO("Médio"),
    ALTO("Alto");

    private final String descricao;

    PerfilRisco(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean permiteInvestimentoArrojado() {
        return this == ARROJADO || this == ALTO;
    }

    public boolean permiteInvestimentoModerado() {
        return this == MODERADO || this == MEDIO || this.permiteInvestimentoArrojado();
    }

    public boolean permiteInvestimentoConservador() {
        return true;
    }
}