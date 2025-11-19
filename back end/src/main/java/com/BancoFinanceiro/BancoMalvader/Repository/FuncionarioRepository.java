package com.BancoFinanceiro.BancoMalvader.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.Funcionario;
import com.BancoFinanceiro.BancoMalvader.model.enums.CargoFuncionario;

/**
 * Repositório para entidade Funcionario
 * Operações de persistência para funcionários
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByMatricula(String matricula);

    Page<Funcionario> findByCargo(CargoFuncionario cargo, Pageable pageable);

    Page<Funcionario> findByAtivo(Boolean ativo, Pageable pageable);

    boolean existsByMatricula(String matricula);
}