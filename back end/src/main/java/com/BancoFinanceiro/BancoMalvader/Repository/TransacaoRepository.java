package com.BancoFinanceiro.BancoMalvader.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.Transacao;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoTransacao;

/**
 * Repositório para entidade Transacao
 * Operações de persistência para transações financeiras
 */
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    Page<Transacao> findByContaOrigemId(Long contaId, Pageable pageable);

    Page<Transacao> findByContaDestinoId(Long contaId, Pageable pageable);

    Page<Transacao> findByTipoTransacao(TipoTransacao tipoTransacao, Pageable pageable);

    @Query("SELECT t FROM Transacao t WHERE t.contaOrigem.id = :contaId OR t.contaDestino.id = :contaId")
    Page<Transacao> findTodasTransacoesPorConta(@Param("contaId") Long contaId, Pageable pageable);

    @Query("SELECT t FROM Transacao t WHERE t.dataTransacao BETWEEN :inicio AND :fim")
    Page<Transacao> findByPeriodo(@Param("inicio") LocalDateTime inicio,
                                  @Param("fim") LocalDateTime fim, Pageable pageable);

    @Query("SELECT SUM(t.valor) FROM Transacao t WHERE t.tipoTransacao = :tipo AND t.dataTransacao BETWEEN :inicio AND :fim")
    Optional<Double> getSomaTransacoesPorTipoEPeriodo(@Param("tipo") TipoTransacao tipo,
                                                      @Param("inicio") LocalDateTime inicio,
                                                      @Param("fim") LocalDateTime fim);

    @Query("SELECT t FROM Transacao t WHERE t.contaOrigem.cliente.id = :clienteId")
    Page<Transacao> findByClienteId(@Param("clienteId") Long clienteId, Pageable pageable);
}