package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para tipos de relatórios disponíveis
 * Classifica relatórios por finalidade e conteúdo
 */
public enum TipoRelatorio {
    EXTRATO_BANCARIO("Extrato Bancário"),
    MOVIMENTACOES_CLIENTE("Movimentações por Cliente"),
    TRANSACOES_PERIODICAS("Transações Periódicas"),
    CLIENTES_ATIVOS("Clientes Ativos"),
    CONTAS_BLOQUEADAS("Contas Bloqueadas"),
    TAXAS_COBRADAS("Taxas Cobradas"),
    AUDITORIA_ACESSOS("Auditoria de Acessos"),
    PERFORMANCE_INVESTIMENTOS("Performance de Investimentos");

    private final String descricao;

    TipoRelatorio(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean requerAcessoAdministrativo() {
        return this == CLIENTES_ATIVOS || this == CONTAS_BLOQUEADAS || this == AUDITORIA_ACESSOS;
    }

    public boolean eFinanceiro() {
        return this == EXTRATO_BANCARIO || this == MOVIMENTACOES_CLIENTE ||
                this == TRANSACOES_PERIODICAS || this == TAXAS_COBRADAS ||
                this == PERFORMANCE_INVESTIMENTOS;
    }
}