package com.BancoFinanceiro.BancoMalvader.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de encerramento de conta
 * Contém motivo e dados para encerramento
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncerramentoContaRequest {

    @NotBlank(message = "Motivo é obrigatório")
    private String motivo;

    private String senhaConfirmacao;
}