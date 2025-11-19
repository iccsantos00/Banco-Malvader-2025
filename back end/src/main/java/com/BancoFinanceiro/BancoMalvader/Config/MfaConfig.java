package com.BancoFinanceiro.BancoMalvader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.ICredentialRepository;
import java.util.List;

/**
 * Configuração para Google Authenticator (MFA)
 * Define beans para autenticação de dois fatores
 */
@Configuration
public class MfaConfig {

    @Bean
    public GoogleAuthenticator googleAuthenticator() {
        GoogleAuthenticator authenticator = new GoogleAuthenticator();
        authenticator.setCredentialRepository(customCredentialRepository());
        return authenticator;
    }

    @Bean
    public ICredentialRepository customCredentialRepository() {
        return new CustomCredentialRepository();
    }

    /**
     * Implementação customizada do repositório de credenciais
     * Para integração com banco de dados
     */
    private static class CustomCredentialRepository implements ICredentialRepository {

        @Override
        public String getSecretKey(String userName) {
            return null;
        }

        @Override
        public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
        }
    }
}