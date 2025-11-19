package com.BancoFinanceiro.BancoMalvader.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de recuperação de senha
 * Contém status da operação de redefinição
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecuperacaoSenhaResponse {

    private Boolean sucesso;
    private String mensagem;
    private String email;

    public RecuperacaoSenhaResponse(Boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }
}