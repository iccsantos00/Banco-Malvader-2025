package com.BancoFinanceiro.BancoMalvader.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.BancoFinanceiro.BancoMalvader.model.dto.LoginRequest;
import com.BancoFinanceiro.BancoMalvader.model.dto.LoginResponse;
import com.BancoFinanceiro.BancoMalvader.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller para operações de autenticação
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para autenticação de usuários com suporte a MFA
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Recebida requisição de login para: {}", loginRequest.getEmail());

        try {
            LoginResponse response = authService.login(loginRequest);
            log.info("Login processado com sucesso para: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);

        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            log.warn("Credenciais inválidas para: {} - {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.status(401).body(
                    new LoginResponse("Credenciais inválidas: " + e.getMessage())
            );
        } catch (Exception e) {
            log.error("Erro no login para {}: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body(
                    new LoginResponse("Erro na autenticação: " + e.getMessage())
            );
        }
    }

    /**
     * Endpoint para logout do usuário
     */
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {
        try {
            String userEmail = getCurrentUserEmail();
            log.info("Recebida requisição de logout para: {}", userEmail);

            authService.logout();

            log.info("Logout realizado com sucesso para: {}", userEmail);
            return ResponseEntity.ok(new LogoutResponse(true, "Logout realizado com sucesso", userEmail));

        } catch (SecurityException e) {
            log.warn("Tentativa de logout sem autenticação: {}", e.getMessage());
            return ResponseEntity.status(401).body(new LogoutResponse(false, "Usuário não autenticado", null));
        } catch (Exception e) {
            log.error("Erro durante logout: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(new LogoutResponse(false, "Erro durante logout: " + e.getMessage(), null));
        }
    }

    /**
     * Endpoint para alteração de senha do usuário autenticado
     */
    @PostMapping("/alterar-senha")
    public ResponseEntity<AlterarSenhaResponse> alterarSenha(@Valid @RequestBody AlterarSenhaRequest changePasswordRequest) {
        try {
            String userEmail = getCurrentUserEmail();
            log.info("Recebida requisição para alteração de senha para: {}", userEmail);

            // Valida se as senhas são diferentes
            if (changePasswordRequest.getSenhaAtual().equals(changePasswordRequest.getNovaSenha())) {
                return ResponseEntity.badRequest()
                        .body(new AlterarSenhaResponse(false, "A nova senha deve ser diferente da senha atual"));
            }

            authService.alterarSenha(
                    changePasswordRequest.getSenhaAtual(),
                    changePasswordRequest.getNovaSenha()
            );

            log.info("Senha alterada com sucesso para: {}", userEmail);
            return ResponseEntity.ok(new AlterarSenhaResponse(true, "Senha alterada com sucesso"));

        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            log.warn("Senha atual incorreta para: {}", getCurrentUserEmail());
            return ResponseEntity.badRequest()
                    .body(new AlterarSenhaResponse(false, "Senha atual incorreta"));
        } catch (SecurityException e) {
            log.warn("Tentativa de alteração de senha sem autenticação: {}", e.getMessage());
            return ResponseEntity.status(401)
                    .body(new AlterarSenhaResponse(false, "Usuário não autenticado"));
        } catch (Exception e) {
            log.error("Erro ao alterar senha para {}: {}", getCurrentUserEmail(), e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new AlterarSenhaResponse(false, "Erro ao alterar senha: " + e.getMessage()));
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
     * DTO interno para requisição de alteração de senha
     */
    public static class AlterarSenhaRequest {
        private String senhaAtual;
        private String novaSenha;

        // Getters e Setters
        public String getSenhaAtual() { return senhaAtual; }
        public void setSenhaAtual(String senhaAtual) { this.senhaAtual = senhaAtual; }

        public String getNovaSenha() { return novaSenha; }
        public void setNovaSenha(String novaSenha) { this.novaSenha = novaSenha; }
    }

    /**
     * DTO para resposta de alteração de senha
     */
    public static class AlterarSenhaResponse {
        private boolean sucesso;
        private String mensagem;

        public AlterarSenhaResponse(boolean sucesso, String mensagem) {
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
     * DTO para resposta de logout
     */
    public static class LogoutResponse {
        private boolean sucesso;
        private String mensagem;
        private String email;

        public LogoutResponse(boolean sucesso, String mensagem, String email) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
            this.email = email;
        }

        // Getters e Setters
        public boolean isSucesso() { return sucesso; }
        public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }

        public String getMensagem() { return mensagem; }
        public void setMensagem(String mensagem) { this.mensagem = mensagem; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}