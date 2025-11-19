package com.BancoFinanceiro.BancoMalvader.service;

import com.BancoFinanceiro.BancoMalvader.model.dto.RelatorioRequest;
import com.BancoFinanceiro.BancoMalvader.model.entity.Relatorio;

/**
 * Serviço para geração de relatórios
 * Define operações para criação e gestão de relatórios
 */
public interface RelatorioService {

    Relatorio gerarRelatorio(RelatorioRequest relatorioRequest);

    byte[] baixarRelatorio(Long relatorioId);

    Relatorio buscarRelatorioPorId(Long id);
}