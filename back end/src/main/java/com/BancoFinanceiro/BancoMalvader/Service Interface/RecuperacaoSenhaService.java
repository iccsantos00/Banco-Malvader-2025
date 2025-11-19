package com.BancoFinanceiro.BancoMalvader.service;

import com.BancoFinanceiro.BancoMalvader.model.dto.RecuperacaoSenhaRequest;
import com.BancoFinanceiro.BancoMalvader.model.dto.RecuperacaoSenhaResponse;
import com.BancoFinanceiro.BancoMalvader.model.dto.ValidacaoTokenRequest;

/**
 * Serviço para recuperação de senha
 * Define fluxo de redefinição de senha por email
 */
public interface RecuperacaoSenhaService {

    RecuperacaoSenhaResponse solicitarRecuperacao(RecuperacaoSenhaRequest request);

    RecuperacaoSenhaResponse validarToken(ValidacaoTokenRequest request);

    boolean isTokenValido(String token);
}