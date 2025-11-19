package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para formatos de relatórios
 * Define formatos de saída para geração
 */
public enum FormatoRelatorio {
    PDF("pdf", "application/pdf"),
    EXCEL("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    CSV("csv", "text/csv"),
    HTML("html", "text/html");

    private final String extensao;
    private final String contentType;

    FormatoRelatorio(String extensao, String contentType) {
        this.extensao = extensao;
        this.contentType = contentType;
    }

    public String getExtensao() {
        return extensao;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean suportaPaginas() {
        return this == PDF || this == HTML;
    }

    public boolean ePlanilha() {
        return this == EXCEL || this == CSV;
    }
}