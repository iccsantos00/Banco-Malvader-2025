package com.BancoFinanceiro.BancoMalvader.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.BancoFinanceiro.BancoMalvader.model.entity.Cliente;
import com.BancoFinanceiro.BancoMalvader.model.enums.StatusCliente;

/**
 * Serviço para gestão de clientes
 * Define operações CRUD e business logic para clientes
 */
public interface ClienteService {

    Cliente buscarPorId(Long id);

    Cliente buscarPorCpf(String cpf);

    Page<Cliente> listarTodos(Pageable pageable);

    Page<Cliente> listarPorStatus(StatusCliente status, Pageable pageable);

    Cliente salvar(Cliente cliente);

    Cliente atualizarStatus(Long id, StatusCliente status);

    void bloquearCliente(Long id, String motivo);
}