package com.BancoFinanceiro.BancoMalvader.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.Relatorio;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoRelatorio;

/**
 * Repositório para entidade Relatorio
 * Operações de persistência para relatórios
 */
@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

    Page<Relatorio> findByTipoRelatorio(TipoRelatorio tipoRelatorio, Pageable pageable);

    Page<Relatorio> findByUsuarioSolicitanteId(Long usuarioId, Pageable pageable);
}