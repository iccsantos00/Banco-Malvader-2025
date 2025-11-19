package com.BancoFinanceiro.BancoMalvader.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.TokenRecuperacaoSenha;

/**
 * Repositório para tokens de recuperação de senha
 * Operações de persistência para tokens
 */
@Repository
public interface TokenRecuperacaoSenhaRepository extends JpaRepository<TokenRecuperacaoSenha, Long> {

    Optional<TokenRecuperacaoSenha> findByToken(String token);

    Optional<TokenRecuperacaoSenha> findByTokenAndUtilizadoFalse(String token);

    void deleteByUsuarioId(Long usuarioId);

    long countByUsuarioIdAndUtilizadoFalse(Long usuarioId);
}