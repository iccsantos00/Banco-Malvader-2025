package com.BancoFinanceiro.BancoMalvader.service.impl;

import org.springframework.stereotype.Service;
import com.BancoFinanceiro.BancoMalvader.model.entity.Usuario;
import com.BancoFinanceiro.BancoMalvader.repository.UsuarioRepository;
import com.BancoFinanceiro.BancoMalvader.service.MfaService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do serviço de MFA
 * Gerencia autenticação de dois fatores com Google Authenticator
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MfaServiceImpl implements MfaService {

    private final GoogleAuthenticator googleAuthenticator;
    private final UsuarioRepository usuarioRepository;

    @Override
    public String gerarChaveSecreta() {
        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
        String chaveSecreta = key.getKey();

        log.info("Nova chave secreta MFA gerada");
        return chaveSecreta;
    }

    @Override
    public String gerarQrCodeUrl(String email, String chaveSecreta, String issuer) {
        String qrCodeUrl = GoogleAuthenticatorQRGenerator.getOtpAuthURL(
                issuer,
                email,
                new GoogleAuthenticatorKey.Builder(chaveSecreta).build()
        );

        log.debug("QR Code URL gerado para: {}", email);
        return qrCodeUrl;
    }

    @Override
    public boolean validarCodigoMfa(String email, String codigoMfa) {
        try {
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            if (!usuario.getMfaHabilitado() || usuario.getMfaSecret() == null) {
                log.warn("Tentativa de validação MFA para usuário sem MFA habilitado: {}", email);
                return false;
            }

            int codigo = Integer.parseInt(codigoMfa);
            boolean isValid = googleAuthenticator.authorize(usuario.getMfaSecret(), codigo);

            log.debug("Validação MFA para {}: {}", email, isValid ? "VÁLIDO" : "INVÁLIDO");
            return isValid;

        } catch (NumberFormatException e) {
            log.warn("Código MFA inválido (não numérico) para: {}", email);
            return false;
        } catch (Exception e) {
            log.error("Erro ao validar código MFA para {}: {}", email, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean habilitarMfa(String email, String chaveSecreta, String codigoMfa) {
        try {
            int codigo = Integer.parseInt(codigoMfa);
            boolean codigoValido = googleAuthenticator.authorize(chaveSecreta, codigo);

            if (!codigoValido) {
                log.warn("Código MFA inválido ao tentar habilitar para: {}", email);
                return false;
            }

            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            usuario.setMfaSecret(chaveSecreta);
            usuario.setMfaHabilitado(true);
            usuarioRepository.save(usuario);

            log.info("MFA habilitado com sucesso para: {}", email);
            return true;

        } catch (Exception e) {
            log.error("Erro ao habilitar MFA para {}: {}", email, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean desabilitarMfa(String email, String codigoMfa) {
        try {
            if (!validarCodigoMfa(email, codigoMfa)) {
                log.warn("Código MFA inválido ao tentar desabilitar para: {}", email);
                return false;
            }

            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            usuario.setMfaSecret(null);
            usuario.setMfaHabilitado(false);
            usuarioRepository.save(usuario);

            log.info("MFA desabilitado com sucesso para: {}", email);
            return true;

        } catch (Exception e) {
            log.error("Erro ao desabilitar MFA para {}: {}", email, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isMfaHabilitado(String email) {
        try {
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            return usuario.getMfaHabilitado() && usuario.getMfaSecret() != null;

        } catch (Exception e) {
            log.error("Erro ao verificar status MFA para {}: {}", email, e.getMessage());
            return false;
        }
    }
}