package com.BancoFinanceiro.BancoMalvader.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.BancoFinanceiro.BancoMalvader.model.entity.Funcionario;
import com.BancoFinanceiro.BancoMalvader.model.enums.CargoFuncionario;

/**
 * Serviço para gestão de funcionários
 * Define operações para funcionários do banco
 */
public interface FuncionarioService {

    Funcionario buscarPorId(Long id);

    Funcionario buscarPorMatricula(String matricula);

    Page<Funcionario> listarTodos(Pageable pageable);

    Page<Funcionario> listarPorCargo(CargoFuncionario cargo, Pageable pageable);

    Funcionario salvar(Funcionario funcionario);

    void desativarFuncionario(Long id, String motivo);
}