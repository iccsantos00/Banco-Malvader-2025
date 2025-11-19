package com.BancoFinanceiro.BancoMalvader.service;

/**
 * Serviço para autenticação de dois fatores
 * Define operações para configuração e validação MFA
 */
public interface MfaService {

    String gerarChaveSecreta();

    String gerarQrCodeUrl(String email, String chaveSecreta, String issuer);

    boolean validarCodigoMfa(String email, String codigoMfa);

    boolean habilitarMfa(String email, String chaveSecreta, String codigoMfa);

    boolean desabilitarMfa(String email, String codigoMfa);

    boolean isMfaHabilitado(String email);
}