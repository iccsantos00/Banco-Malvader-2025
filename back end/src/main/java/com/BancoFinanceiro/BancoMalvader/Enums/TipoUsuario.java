package com.BancoFinanceiro.BancoMalvader.model.enums;

/**
 * Enumeração para tipos de usuários do sistema
 * Define papéis e permissões de acesso
 */
public enum TipoUsuario {
    CLIENTE("ROLE_CLIENTE"),
    FUNCIONARIO("ROLE_FUNCIONARIO"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    TipoUsuario(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static TipoUsuario fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
        try {
            return TipoUsuario.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de usuário inválido: " + value);
        }
    }

    public boolean isFuncionario() {
        return this == FUNCIONARIO || this == ADMIN;
    }

    public boolean isCliente() {
        return this == CLIENTE;
    }
}