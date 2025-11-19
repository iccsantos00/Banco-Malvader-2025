package com.BancoFinanceiro.BancoMalvader.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.BancoFinanceiro.BancoMalvader.model.entity.Cliente;
import com.BancoFinanceiro.BancoMalvader.model.enums.StatusCliente;
import com.BancoFinanceiro.BancoMalvader.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller para operações relacionadas a clientes
 * Expõe endpoints para consulta e gestão de clientes
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clientes", description = "Endpoints para gestão de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FUNCIONARIO') or hasRole('ADMIN')")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna dados completos de um cliente específico")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        log.info("Buscando cliente por ID: {}", id);

        try {
            Cliente cliente = clienteService.buscarPorId(id);
            return ResponseEntity.ok(cliente);

        } catch (Exception e) {
            log.error("Erro ao buscar cliente ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasRole('FUNCIONARIO') or hasRole('ADMIN')")
    @Operation(summary = "Buscar cliente por CPF", description = "Retorna dados completos de um cliente pelo CPF")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        log.info("Buscando cliente por CPF: {}", cpf);

        try {
            Cliente cliente = clienteService.buscarPorCpf(cpf);
            return ResponseEntity.ok(cliente);

        } catch (Exception e) {
            log.error("Erro ao buscar cliente CPF {}: {}", cpf, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('FUNCIONARIO') or hasRole('ADMIN')")
    @Operation(summary = "Listar clientes", description = "Retorna lista paginada de todos os clientes")
    public ResponseEntity<Page<Cliente>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort) {

        log.info("Listando clientes - página: {}, tamanho: {}, ordenação: {}", page, size, sort);

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            Page<Cliente> clientes = clienteService.listarTodos(pageable);
            return ResponseEntity.ok(clientes);

        } catch (Exception e) {
            log.error("Erro ao listar clientes: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('FUNCIONARIO') or hasRole('ADMIN')")
    @Operation(summary = "Listar clientes por status", description = "Retorna lista paginada de clientes filtrados por status")
    public ResponseEntity<Page<Cliente>> listarPorStatus(
            @PathVariable StatusCliente status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("Listando clientes por status: {} - página: {}, tamanho: {}", status, page, size);

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Cliente> clientes = clienteService.listarPorStatus(status, pageable);
            return ResponseEntity.ok(clientes);

        } catch (Exception e) {
            log.error("Erro ao listar clientes por status {}: {}", status, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar status do cliente", description = "Atualiza status de um cliente específico")
    public ResponseEntity<Cliente> atualizarStatus(
            @PathVariable Long id,
            @RequestBody StatusRequest statusRequest) {

        log.info("Atualizando status do cliente ID: {} para {}", id, statusRequest.getStatus());

        try {
            Cliente cliente = clienteService.atualizarStatus(id, statusRequest.getStatus());
            return ResponseEntity.ok(cliente);

        } catch (Exception e) {
            log.error("Erro ao atualizar status do cliente ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/bloquear")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Bloquear cliente", description = "Bloqueia um cliente específico")
    public ResponseEntity<String> bloquearCliente(
            @PathVariable Long id,
            @RequestBody BloqueioRequest bloqueioRequest) {

        log.warn("Solicitado bloqueio do cliente ID: {} - Motivo: {}", id, bloqueioRequest.getMotivo());

        try {
            clienteService.bloquearCliente(id, bloqueioRequest.getMotivo());
            return ResponseEntity.ok("Cliente bloqueado com sucesso");

        } catch (Exception e) {
            log.error("Erro ao bloquear cliente ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao bloquear cliente: " + e.getMessage());
        }
    }

    public static class StatusRequest {
        private StatusCliente status;

        public StatusCliente getStatus() { return status; }
        public void setStatus(StatusCliente status) { this.status = status; }
    }

    public static class BloqueioRequest {
        private String motivo;

        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }
    }
}