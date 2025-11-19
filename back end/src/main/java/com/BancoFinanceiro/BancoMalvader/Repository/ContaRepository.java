package com.BancoFinanceiro.BancoMalvader.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.Conta;
import com.BancoFinanceiro.BancoMalvader.model.enums.StatusConta;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoConta;

/**
 * Repositório para entidade Conta
 * Operações de persistência para contas bancárias
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByNumeroConta(String numeroConta);

    List<Conta> findByClienteId(Long clienteId);

    Page<Conta> findByTipoConta(TipoConta tipoConta, Pageable pageable);

    Page<Conta> findByStatus(StatusConta status, Pageable pageable);

    @Query("SELECT c FROM Conta c WHERE c.cliente.id = :clienteId AND c.status = 'ATIVA'")
    List<Conta> findContasAtivasByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT SUM(c.saldo) FROM Conta c WHERE c.agencia.id = :agenciaId")
    Optional<Double> getSaldoTotalAgencia(@Param("agenciaId") Long agenciaId);

    @Query("SELECT COUNT(c) FROM Conta c WHERE c.agencia.id = :agenciaId AND c.status = 'ATIVA'")
    Long countContasAtivasByAgencia(@Param("agenciaId") Long agenciaId);

    boolean existsByNumeroConta(String numeroConta);
}