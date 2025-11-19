package com.BancoFinanceiro.BancoMalvader.service;

import com.BancoFinanceiro.BancoMalvader.model.dto.ClienteCadastroRequest;
import com.BancoFinanceiro.BancoMalvader.model.dto.ClienteCadastroResponse;

/**
 * Serviço para cadastro de novos clientes
 * Define fluxo completo de cadastro e aprovação
 */
public interface ClienteCadastroService {

    ClienteCadastroResponse cadastrarCliente(ClienteCadastroRequest cadastroRequest);

    ClienteCadastroResponse aprovarCadastro(Long clienteId, Long funcionarioId);

    ClienteCadastroResponse reprovarCadastro(Long clienteId, Long funcionarioId, String motivo);

    ClienteCadastroResponse buscarStatusCadastro(Long clienteId);
}