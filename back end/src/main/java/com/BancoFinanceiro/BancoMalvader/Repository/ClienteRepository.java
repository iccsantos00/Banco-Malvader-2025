package com.BancoFinanceiro.BancoMalvader.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.Cliente;
import com.BancoFinanceiro.BancoMalvader.model.enums.StatusCliente;

/**
 * Repositório para entidade Cliente
 * Operações de persistência e queries customizadas
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByEmail(String email);

    Page<Cliente> findByStatus(StatusCliente status, Pageable pageable);

    Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query("SELECT c FROM Cliente c WHERE c.endereco.cidade = :cidade")
    Page<Cliente> findByCidade(@Param("cidade") String cidade, Pageable pageable);

    long countByStatus(StatusCliente status);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE c.dataNascimento BETWEEN :dataInicio AND :dataFim")
    Page<Cliente> findByDataNascimentoBetween(
            @Param("dataInicio") java.time.LocalDate dataInicio,
            @Param("dataFim") java.time.LocalDate dataFim,
            Pageable pageable);
}