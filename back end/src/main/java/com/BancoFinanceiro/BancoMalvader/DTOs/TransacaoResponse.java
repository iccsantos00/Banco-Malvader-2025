package com.BancoFinanceiro.BancoMalvader.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de transação
 * Contém informações da transação executada
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoResponse {

    private Long id;
    private TipoTransacao tipoTransacao;
    private BigDecimal valor;
    private LocalDateTime dataTransacao;
    private String descricao;
    private String numeroContaOrigem;
    private String numeroContaDestino;
    private BigDecimal saldoAtual;
    private String status;
}