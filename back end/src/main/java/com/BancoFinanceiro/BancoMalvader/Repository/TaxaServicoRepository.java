package com.BancoFinanceiro.BancoMalvader.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.TaxaServico;

/**
 * Repositório para taxas e serviços
 * Operações de persistência para taxas
 */
@Repository
public interface TaxaServicoRepository extends JpaRepository<TaxaServico, Long> {

    List<TaxaServico> findByAtivoTrue();

    TaxaServico findByCodigoServico(String codigoServico);

    boolean existsByCodigoServico(String codigoServico);
}