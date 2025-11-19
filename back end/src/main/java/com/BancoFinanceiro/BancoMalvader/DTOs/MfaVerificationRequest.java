package com.BancoFinanceiro.BancoMalvader.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para verificação de código MFA
 * Usado para validação durante setup e desativação
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MfaVerificationRequest {

    @NotBlank(message = "Código MFA é obrigatório")
    private String codigoMfa;

    // Chave secreta é opcional - só é necessária durante o setup
    private String chaveSecreta;
}