package com.BancoFinanceiro.BancoMalvader.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.AuditoriaAcesso;

/**
 * Repositório para auditoria de acessos
 * Operações de persistência para logs de acesso
 */
@Repository
public interface AuditoriaAcessoRepository extends JpaRepository<AuditoriaAcesso, Long> {

    Page<AuditoriaAcesso> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<AuditoriaAcesso> findBySucesso(Boolean sucesso, Pageable pageable);

    @Query("SELECT a FROM AuditoriaAcesso a WHERE a.emailTentativa = :email")
    Page<AuditoriaAcesso> findByEmailTentativa(@Param("email") String email, Pageable pageable);

    @Query("SELECT COUNT(a) FROM AuditoriaAcesso a WHERE a.usuario.id = :usuarioId AND a.sucesso = false AND a.dataAcesso > :desde")
    Long countTentativasFalhasRecentes(@Param("usuarioId") Long usuarioId, @Param("desde") java.time.LocalDateTime desde);
}