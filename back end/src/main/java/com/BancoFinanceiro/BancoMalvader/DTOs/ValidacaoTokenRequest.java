package com.BancoFinanceiro.BancoMalvader.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para validação de token de recuperação
 * Verifica se token é válido para redefinição
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidacaoTokenRequest {

    @NotBlank(message = "Token é obrigatório")
    private String token;

    @NotBlank(message = "Nova senha é obrigatória")
    private String novaSenha;
}