package com.BancoFinanceiro.BancoMalvader.model.dto;

import com.BancoFinanceiro.BancoMalvader.model.enums.StatusAprovacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de cadastro de cliente
 * Contém informações do cliente criado e status
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCadastroResponse {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private StatusAprovacao statusAprovacao;
    private String mensagem;

    public ClienteCadastroResponse(Long id, String nome, String email, String cpf, StatusAprovacao statusAprovacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.statusAprovacao = statusAprovacao;
        this.mensagem = "Cadastro realizado com sucesso. Aguarde aprovação.";
    }
}