package com.BancoFinanceiro.BancoMalvader.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.BancoFinanceiro.BancoMalvader.model.entity.Usuario;

/**
 * Repositório para entidade Usuario
 * Operações de persistência para usuários
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);
}