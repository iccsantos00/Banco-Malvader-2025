package com.BancoFinanceiro.BancoMalvader.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de setup do MFA
 * Contém chave secreta e código de verificação
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MfaSetupRequest {

    @NotBlank(message = "Chave secreta é obrigatória")
    private String chaveSecreta;

    @NotBlank(message = "Código MFA é obrigatório")
    private String codigoMfa;
}