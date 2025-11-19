package com.BancoFinanceiro.BancoMalvader.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.BancoFinanceiro.BancoMalvader.model.entity.AuditoriaAcesso;

/**
 * Serviço para auditoria do sistema
 * Define operações para registro e consulta de logs
 */
public interface AuditoriaService {

    void registrarAcesso(String email, String enderecoIp, String userAgent, Boolean sucesso, String motivoFalha);

    Page<AuditoriaAcesso> listarAcessos(Pageable pageable);

    Page<AuditoriaAcesso> listarAcessosPorUsuario(Long usuarioId, Pageable pageable);

    Page<AuditoriaAcesso> listarAcessosPorIp(String enderecoIp, Pageable pageable);

    Long contarTentativasFalhasRecentes(Long usuarioId);
}