package com.BancoFinanceiro.BancoMalvader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.Endereco;

/**
 * Repositório para entidade Endereco
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    // Métodos customizados podem ser adicionados aqui se necessário
}