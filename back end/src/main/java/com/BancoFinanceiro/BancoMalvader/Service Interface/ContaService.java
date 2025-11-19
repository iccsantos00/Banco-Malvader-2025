package com.BancoFinanceiro.BancoMalvader.service;

import java.math.BigDecimal;
import java.util.List;
import com.BancoFinanceiro.BancoMalvader.model.entity.Conta;
import com.BancoFinanceiro.BancoMalvader.model.enums.StatusConta;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoConta;

/**
 * Serviço para gestão de contas bancárias
 * Define operações para contas e movimentações
 */
public interface ContaService {

    Conta buscarPorId(Long id);

    Conta buscarPorNumero(String numeroConta);

    List<Conta> buscarPorCliente(Long clienteId);

    Conta criarConta(Conta conta, TipoConta tipoConta);

    void encerrarConta(Long contaId, String motivo);

    void bloquearConta(Long contaId, String motivo);

    void depositar(Long contaId, BigDecimal valor);

    void sacar(Long contaId, BigDecimal valor);

    void transferir(Long contaOrigemId, Long contaDestinoId, BigDecimal valor);

    BigDecimal consultarSaldo(Long contaId);

    Conta atualizarStatus(Long contaId, StatusConta status);
}