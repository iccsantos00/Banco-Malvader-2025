package com.BancoFinanceiro.BancoMalvader.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de recuperação de senha
 * Inicia processo de redefinição de senha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecuperacaoSenhaRequest {

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
}