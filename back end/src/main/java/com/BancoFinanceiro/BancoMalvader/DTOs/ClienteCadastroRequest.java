package com.BancoFinanceiro.BancoMalvader.model.dto;

import java.time.LocalDate;
import com.BancoFinanceiro.BancoMalvader.model.enums.PerfilRisco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de cadastro de cliente
 * Contém todos os dados necessários para criar novo cliente
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCadastroRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String senha;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres")
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    private String profissao;

    private Double rendaMensal;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "Estado deve ser a sigla com 2 caracteres")
    private String estado;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @NotNull(message = "Perfil de risco é obrigatório")
    private PerfilRisco perfilRisco = PerfilRisco.BAIXO;
}