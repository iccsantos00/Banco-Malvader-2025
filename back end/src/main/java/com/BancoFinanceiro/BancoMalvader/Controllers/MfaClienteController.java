package com.BancoFinanceiro.BancoMalvader.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.BancoFinanceiro.BancoMalvader.model.dto.MfaSetupRequest;
import com.BancoFinanceiro.BancoMalvader.model.dto.MfaVerificationRequest;
import com.BancoFinanceiro.BancoMalvader.service.MfaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller para operações de MFA (Autenticação de Dois Fatores)
 * Permite configurar e gerenciar Google Authenticator para usuários
 */
@RestController
@RequestMapping("/api/mfa")
@RequiredArgsConstructor
@Slf4j
public class MfaClienteController {

    private final MfaService mfaService;

    /**
     * Inicia configuração do MFA gerando chave secreta e QR Code
     */
    @GetMapping("/setup")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('FUNCIONARIO') or hasRole('ADMIN')")
    public ResponseEntity<MfaSetupResponse> iniciarSetupMfa() {
        try {
            String userEmail = getCurrentUserEmail();

            // Gera nova chave secreta
            String chaveSecreta = mfaService.gerarChaveSecreta();

            // Gera QR Code URL para o Google Authenticator
            String qrCodeUrl = mfaService.gerarQrCodeUrl(userEmail, chaveSecreta, "BancoMalvader");

            MfaSetupResponse response = new MfaSetupResponse(
                    chaveSecreta,
                    qrCodeUrl,
                    "Escaneie o QR Code com o Google Authenticator e depois valide com o código gerado"
            );

            log.info("Setup MFA iniciado para: {}", userEmail);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Erro ao iniciar setup MFA: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Verifica e ativa MFA com código de verificação do Google Authenticator
     */
    @PostMapping("/verify")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('FUNCIONARIO') or hasRole('ADMIN')")
    public ResponseEntity<MfaActivationResponse> verificarEAtivarMfa(@Valid @RequestBody MfaSetupRequest request) {
        try {
            String userEmail = getCurrentUserEmail();

            // Habilita MFA após validação do código
            boolean sucesso = mfaService.habilitarMfa(
                    userEmail,
                    request.getChaveSecreta(),
                    request.getCodigoMfa()
            );

            if (sucesso) {
                log.info("MFA ativado com sucesso para: {}", userEmail);
                return ResponseEntity.ok(new MfaActivationResponse(true, "MFA ativado com sucesso"));
            } else {
                log.warn("Falha ao ativar MFA para: {} - código inválido", userEmail);
                return ResponseEntity.badRequest().body(new MfaActivationResponse(false, "Código MFA inválido"));
            }

        } catch (Exception e) {
            log.error("Erro ao ativar MFA: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(new MfaActivationResponse(false, "Erro ao ativar MFA: " + e.getMessage()));
        }
    }

    /**
     * Desabilita MFA para o usuário atual após verificação do código
     */
    @PostMapping("/disable")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('FUNCIONARIO') or hasRole('ADMIN')")
    public ResponseEntity<MfaActivationResponse> desabilitarMfa(@Valid @RequestBody MfaVerificationRequest request) {
        try {
            String userEmail = getCurrentUserEmail();

            // Desabilita MFA após validação do código atual
            boolean sucesso = mfaService.desabilitarMfa(userEmail, request.getCodigoMfa());

            if (sucesso) {
                log.info("MFA desabilitado com sucesso para: {}", userEmail);
                return ResponseEntity.ok(new MfaActivationResponse(true, "MFA desabilitado com sucesso"));
            } else {
                log.warn("Falha ao desabilitar MFA para: {} - código inválido", userEmail);
                return ResponseEntity.badRequest().body(new MfaActivationResponse(false, "Código MFA inválido"));
            }

        } catch (Exception e) {
            log.error("Erro ao desabilitar MFA: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(new MfaActivationResponse(false, "Erro ao desabilitar MFA: " + e.getMessage()));
        }
    }

    /**
     * Verifica status do MFA para usuário atual
     */
    @GetMapping("/status")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('FUNCIONARIO') or hasRole('ADMIN')")
    public ResponseEntity<MfaStatusResponse> verificarStatusMfa() {
        try {
            String userEmail = getCurrentUserEmail();
            boolean mfaHabilitado = mfaService.isMfaHabilitado(userEmail);

            MfaStatusResponse response = new MfaStatusResponse(mfaHabilitado, userEmail, "Status verificado com sucesso");
            log.debug("Status MFA verificado para {}: {}", userEmail, mfaHabilitado);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Erro ao verificar status MFA: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(new MfaStatusResponse(false, "Erro ao verificar status: " + e.getMessage()));
        }
    }

    /**
     * Obtém email do usuário atual do contexto de segurança Spring
     */
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        throw new SecurityException("Usuário não autenticado");
    }

    /**
     * DTO para resposta de setup do MFA
     */
    public static class MfaSetupResponse {
        private String chaveSecreta;
        private String qrCodeUrl;
        private String instrucoes;

        public MfaSetupResponse(String chaveSecreta, String qrCodeUrl, String instrucoes) {
            this.chaveSecreta = chaveSecreta;
            this.qrCodeUrl = qrCodeUrl;
            this.instrucoes = instrucoes;
        }

        // Getters e Setters
        public String getChaveSecreta() { return chaveSecreta; }
        public void setChaveSecreta(String chaveSecreta) { this.chaveSecreta = chaveSecreta; }

        public String getQrCodeUrl() { return qrCodeUrl; }
        public void setQrCodeUrl(String qrCodeUrl) { this.qrCodeUrl = qrCodeUrl; }

        public String getInstrucoes() { return instrucoes; }
        public void setInstrucoes(String instrucoes) { this.instrucoes = instrucoes; }
    }

    /**
     * DTO para resposta de ativação/desativação do MFA
     */
    public static class MfaActivationResponse {
        private boolean sucesso;
        private String mensagem;

        public MfaActivationResponse(boolean sucesso, String mensagem) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
        }

        // Getters e Setters
        public boolean isSucesso() { return sucesso; }
        public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }

        public String getMensagem() { return mensagem; }
        public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    }

    /**
     * DTO para resposta de status do MFA
     */
    public static class MfaStatusResponse {
        private boolean mfaHabilitado;
        private String email;
        private String mensagem;

        // Construtor principal com todos os campos
        public MfaStatusResponse(boolean mfaHabilitado, String email, String mensagem) {
            this.mfaHabilitado = mfaHabilitado;
            this.email = email;
            this.mensagem = mensagem;
        }

        // Construtor para sucesso - sem email
        public MfaStatusResponse(boolean mfaHabilitado, String mensagem) {
            this.mfaHabilitado = mfaHabilitado;
            this.mensagem = mensagem;
            this.email = null;
        }

        // Getters e Setters
        public boolean isMfaHabilitado() { return mfaHabilitado; }
        public void setMfaHabilitado(boolean mfaHabilitado) { this.mfaHabilitado = mfaHabilitado; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getMensagem() { return mensagem; }
        public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    }
}