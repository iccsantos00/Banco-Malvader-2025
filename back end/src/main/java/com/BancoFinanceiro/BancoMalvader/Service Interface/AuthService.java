package com.BancoFinanceiro.BancoMalvader.service;

import com.BancoFinanceiro.BancoMalvader.model.dto.LoginRequest;
import com.BancoFinanceiro.BancoMalvader.model.dto.LoginResponse;

/**
 * Serviço para operações de autenticação
 * Define contratos para login, logout e gestão de sessões
 */
public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

    void logout();

    void alterarSenha(String senhaAtual, String novaSenha);
}