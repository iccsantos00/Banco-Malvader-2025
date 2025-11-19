package com.BancoFinanceiro.BancoMalvader.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.BancoFinanceiro.BancoMalvader.model.dto.LoginRequest;
import com.BancoFinanceiro.BancoMalvader.model.dto.LoginResponse;
import com.BancoFinanceiro.BancoMalvader.model.entity.Usuario;
import com.BancoFinanceiro.BancoMalvader.repository.UsuarioRepository;
import com.BancoFinanceiro.BancoMalvader.service.AuthService;
import com.BancoFinanceiro.BancoMalvader.service.MfaService;
import com.BancoFinanceiro.BancoMalvader.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do serviço de autenticação
 * Gerencia login, logout e alteração de senha
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MfaService mfaService;

    /**
     * Processa tentativa de login do usuário
     */
    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Tentativa de login para email: {}", loginRequest.getEmail());

        try {
            // Autentica usuário com Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            // Define autenticação no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtém usuário autenticado
            Usuario usuario = (Usuario) authentication.getPrincipal();

            // Verifica se usuário está ativo
            if (!usuario.getAtivo()) {
                log.warn("Tentativa de login para usuário inativo: {}", loginRequest.getEmail());
                throw new BadCredentialsException("Conta desativada");
            }

            // Verifica se MFA está habilitado
            if (usuario.getMfaHabilitado()) {
                return processarLoginComMfa(usuario, loginRequest.getCodigoMfa());
            }

            // Login sem MFA - gera token diretamente
            return gerarRespostaLogin(usuario);

        } catch (BadCredentialsException e) {
            log.warn("Credenciais inválidas para email: {}", loginRequest.getEmail());
            throw new BadCredentialsException("Email ou senha incorretos");
        }
    }

    /**
     * Processa login quando MFA está habilitado para o usuário
     */
    private LoginResponse processarLoginComMfa(Usuario usuario, String codigoMfa) {
        // Se código MFA não foi fornecido, solicita ao usuário
        if (codigoMfa == null || codigoMfa.trim().isEmpty()) {
            log.info("MFA requerido para usuário: {}", usuario.getEmail());
            return new LoginResponse("Código MFA requerido para autenticação");
        }

        // Valida código MFA fornecido
        if (mfaService.validarCodigoMfa(usuario.getEmail(), codigoMfa)) {
            log.info("MFA validado com sucesso para usuário: {}", usuario.getEmail());
            return gerarRespostaLogin(usuario);
        } else {
            log.warn("Código MFA inválido para usuário: {}", usuario.getEmail());
            throw new BadCredentialsException("Código MFA inválido");
        }
    }

    /**
     * Gera resposta de login bem-sucedido com token JWT
     */
    private LoginResponse gerarRespostaLogin(Usuario usuario) {
        // Gera token JWT
        String jwtToken = jwtUtil.generateToken(usuario);

        // Atualiza data do último login
        usuario.setDataUltimoLogin(java.time.LocalDateTime.now());
        usuarioRepository.save(usuario);

        log.info("Login realizado com sucesso para usuário: {}", usuario.getEmail());

        // Retorna resposta com token
        return new LoginResponse(
                jwtToken,
                jwtUtil.getExpirationTime(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipoUsuario()
        );
    }

    /**
     * Realiza logout do usuário atual
     */
    @Override
    public void logout() {
        // Em sistema stateless com JWT, o logout é feito no cliente
        // removendo o token. Aqui podemos limpar o contexto de segurança.
        SecurityContextHolder.clearContext();
        log.info("Usuário realizou logout");
    }

    /**
     * Altera senha do usuário autenticado
     */
    @Override
    @Transactional
    public void alterarSenha(String senhaAtual, String novaSenha) {
        // Obtém usuário autenticado do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

        // Verifica senha atual
        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new BadCredentialsException("Senha atual incorreta");
        }

        // Define nova senha criptografada
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        log.info("Senha alterada com sucesso para usuário: {}", email);
    }
}