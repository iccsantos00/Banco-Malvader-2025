package com.BancoFinanceiro.BancoMalvader.model.dto;

import com.BancoFinanceiro.BancoMalvader.model.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de login bem-sucedido
 * Contém token de acesso e informações do usuário autenticado
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private String nomeUsuario;
    private String email;
    private TipoUsuario tipoUsuario;
    private Boolean mfaRequerido;
    private String mensagem;

    public LoginResponse(String accessToken, Long expiresIn, String nomeUsuario,
                         String email, TipoUsuario tipoUsuario) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.mfaRequerido = false;
        this.mensagem = "Login realizado com sucesso";
    }

    public LoginResponse(String mensagem) {
        this.mensagem = mensagem;
        this.mfaRequerido = true;
    }
}