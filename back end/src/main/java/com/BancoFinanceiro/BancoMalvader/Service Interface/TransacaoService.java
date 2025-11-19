package com.BancoFinanceiro.BancoMalvader.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.BancoFinanceiro.BancoMalvader.model.dto.TransacaoRequest;
import com.BancoFinanceiro.BancoMalvader.model.dto.TransacaoResponse;
import com.BancoFinanceiro.BancoMalvader.model.entity.Transacao;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoTransacao;

/**
 * Serviço para gestão de transações
 * Define operações para processamento de transações
 */
public interface TransacaoService {

    TransacaoResponse processarTransacao(TransacaoRequest transacaoRequest);

    Page<Transacao> listarTransacoesPorConta(Long contaId, Pageable pageable);

    Page<Transacao> listarTransacoesPorPeriodo(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    Page<Transacao> listarTransacoesPorTipo(TipoTransacao tipo, Pageable pageable);

    BigDecimal calcularTotalTransacoesPorTipo(TipoTransacao tipo, LocalDateTime inicio, LocalDateTime fim);

    Transacao buscarPorId(Long id);
}