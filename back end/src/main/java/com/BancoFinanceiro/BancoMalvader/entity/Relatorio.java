package com.BancoFinanceiro.BancoMalvader.model.entity;

import java.time.LocalDateTime;
import com.BancoFinanceiro.BancoMalvader.model.enums.FormatoRelatorio;
import com.BancoFinanceiro.BancoMalvader.model.enums.TipoRelatorio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade para armazenar relatórios gerados
 * Controla histórico e metadados de relatórios
 */
@Entity
@Table(name = "relatorios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRelatorio tipoRelatorio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormatoRelatorio formato;

    @Column(nullable = false)
    private String nomeArquivo;

    @Lob
    private byte[] conteudo;

    @ManyToOne
    @JoinColumn(name = "usuario_solicitante_id")
    private Usuario usuarioSolicitante;

    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;

    private LocalDateTime dataGeracao;

    @Column(nullable = false)
    private String parametros;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        if (dataSolicitacao == null) {
            dataSolicitacao = LocalDateTime.now();
        }
    }
}