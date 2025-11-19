package com.BancoFinanceiro.BancoMalvader.service;

import java.math.BigDecimal;
import java.util.List;
import com.BancoFinanceiro.BancoMalvader.model.entity.TaxaServico;

/**
 * Serviço para gestão de taxas e serviços
 * Define operações para configuração de tarifas
 */
public interface TaxaService {

    List<TaxaServico> listarTodasTaxas();

    List<TaxaServico> listarTaxasAtivas();

    TaxaServico buscarTaxaPorCodigo(String codigoServico);

    TaxaServico atualizarTaxa(Long taxaId, BigDecimal novoValor);

    TaxaServico criarTaxa(TaxaServico taxaServico);

    void desativarTaxa(Long taxaId);
}