package com.BancoFinanceiro.BancoMalvader.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.LogBloqueioCliente;

/**
 * Repositório para logs de bloqueio de clientes
 * Operações de persistência para logs
 */
@Repository
public interface LogBloqueioClienteRepository extends JpaRepository<LogBloqueioCliente, Long> {

    Page<LogBloqueioCliente> findByClienteId(Long clienteId, Pageable pageable);

    Page<LogBloqueioCliente> findByAcao(String acao, Pageable pageable);
}