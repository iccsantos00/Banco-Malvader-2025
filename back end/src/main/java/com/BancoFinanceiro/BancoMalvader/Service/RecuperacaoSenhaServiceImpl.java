package com.BancoFinanceiro.BancoMalvader.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.BancoFinanceiro.BancoMalvader.model.dto.RecuperacaoSenhaRequest;
import com.BancoFinanceiro.BancoMalvader.model.dto.RecuperacaoSenhaResponse;
import com.BancoFinanceiro.BancoMalvader.model.dto.ValidacaoTokenRequest;
import com.BancoFinanceiro.BancoMalvader.model.entity.TokenRecuperacaoSenha;
import com.BancoFinanceiro.BancoMalvader.model.entity.Usuario;
import com.BancoFinanceiro.BancoMalvader.repository.TokenRecuperacaoSenhaRepository;
import com.BancoFinanceiro.BancoMalvader.repository.UsuarioRepository;
import com.BancoFinanceiro.BancoMalvader.service.RecuperacaoSenhaService;
import com.BancoFinanceiro.BancoMalvader.util.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementação do serviço de recuperação de senha
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RecuperacaoSenhaServiceImpl implements RecuperacaoSenhaService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRecuperacaoSenhaRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    @Override
    @Transactional
    public RecuperacaoSenhaResponse solicitarRecuperacao(RecuperacaoSenhaRequest request) {
        log.info("Solicitando recuperação de senha para: {}", request.getEmail());

        try {
            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Invalida tokens anteriores
            tokenRepository.deleteByUsuarioId(usuario.getId());

            // Cria novo token
            String token = UUID.randomUUID().toString();
            TokenRecuperacaoSenha tokenRecuperacao = new TokenRecuperacaoSenha();
            tokenRecuperacao.setToken(token);
            tokenRecuperacao.setUsuario(usuario);
            tokenRecuperacao.setDataExpiracao(LocalDateTime.now().plusHours(24));
            tokenRecuperacao.setUtilizado(false);

            tokenRepository.save(tokenRecuperacao);

            log.info("Token de recuperação gerado para: {}", request.getEmail());

            // Em produção, aqui enviaria email com o token
            return new RecuperacaoSenhaResponse(true, "Token de recuperação gerado com sucesso", request.getEmail());

        } catch (Exception e) {
            log.error("Erro ao solicitar recuperação: {}", e.getMessage());
            return new RecuperacaoSenhaResponse(false, "Erro ao solicitar recuperação: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RecuperacaoSenhaResponse validarToken(ValidacaoTokenRequest request) {
        log.info("Validando token de recuperação");

        try {
            TokenRecuperacaoSenha tokenRecuperacao = tokenRepository.findByTokenAndUtilizadoFalse(request.getToken())
                    .orElseThrow(() -> new RuntimeException("Token inválido ou já utilizado"));

            if (tokenRecuperacao.isExpirado()) {
                throw new RuntimeException("Token expirado");
            }

            // Valida nova senha
            passwordValidator.isValid(request.getNovaSenha());

            // Atualiza senha do usuário
            Usuario usuario = tokenRecuperacao.getUsuario();
            usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
            usuarioRepository.save(usuario);

            // Marca token como utilizado
            tokenRecuperacao.setUtilizado(true);
            tokenRepository.save(tokenRecuperacao);

            log.info("Senha alterada com sucesso para: {}", usuario.getEmail());
            return new RecuperacaoSenhaResponse(true, "Senha alterada com sucesso", usuario.getEmail());

        } catch (Exception e) {
            log.error("Erro ao validar token: {}", e.getMessage());
            return new RecuperacaoSenhaResponse(false, "Erro ao validar token: " + e.getMessage());
        }
    }

    @Override
    public boolean isTokenValido(String token) {
        try {
            TokenRecuperacaoSenha tokenRecuperacao = tokenRepository.findByTokenAndUtilizadoFalse(token)
                    .orElseThrow(() -> new RuntimeException("Token não encontrado"));

            return !tokenRecuperacao.isExpirado();
        } catch (Exception e) {
            log.warn("Token inválido: {}", token);
            return false;
        }
    }
}