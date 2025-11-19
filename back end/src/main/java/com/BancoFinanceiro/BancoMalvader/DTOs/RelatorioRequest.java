package com.BancoFinanceiro.BancoMalvader.model.dto;

import java.time.LocalDate;
import com.BancoFinanceiro.BancoMalvader.model.enums.FormatoRelatorio;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoRelatorio;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitação de relatório
 * Contém parâmetros para geração de relatórios
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioRequest {

    @NotNull(message = "Tipo de relatório é obrigatório")
    private TipoRelatorio tipoRelatorio;

    @NotNull(message = "Formato é obrigatório")
    private FormatoRelatorio formato;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long clienteId;
    private Long agenciaId;
}