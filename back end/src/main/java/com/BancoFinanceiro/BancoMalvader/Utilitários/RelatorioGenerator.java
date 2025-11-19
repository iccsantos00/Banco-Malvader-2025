package com.BancoFinanceiro.BancoMalvader.util;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import com.BancoFinanceiro.BancoMalvader.model.entity.Transacao;
import com.BancoFinanceiro.BancoMalvader.model.enums.FormatoRelatorio;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * Utilitário para geração de relatórios
 * Suporta múltiplos formatos como PDF, Excel, CSV
 */
@Component
@Slf4j
public class RelatorioGenerator {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public byte[] gerarRelatorioPdf(java.util.List<Transacao> transacoes, String titulo) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            // Título
            Paragraph title = new Paragraph(titulo, TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Tabela de transações
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            // Cabeçalho
            adicionarCabecalhoTabela(table);

            // Dados
            adicionarDadosTabela(table, transacoes);

            document.add(table);
            document.close();

            log.info("Relatório PDF gerado com {} transações", transacoes.size());
            return baos.toByteArray();

        } catch (DocumentException e) {
            log.error("Erro ao gerar relatório PDF: {}", e.getMessage());
            throw new RuntimeException("Erro ao gerar relatório PDF", e);
        }
    }

    private void adicionarCabecalhoTabela(PdfPTable table) {
        String[] headers = {"Data", "Tipo", "Valor", "Conta Origem", "Conta Destino"};

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private void adicionarDadosTabela(PdfPTable table, java.util.List<Transacao> transacoes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Transacao transacao : transacoes) {
            table.addCell(new Phrase(transacao.getDataTransacao().format(formatter), NORMAL_FONT));
            table.addCell(new Phrase(transacao.getTipoTransacao().getDescricao(), NORMAL_FONT));
            table.addCell(new Phrase("R$ " + transacao.getValor().toString(), NORMAL_FONT));
            table.addCell(new Phrase(transacao.getContaOrigem().getNumeroConta(), NORMAL_FONT));

            String contaDestino = transacao.getContaDestino() != null ?
                    transacao.getContaDestino().getNumeroConta() : "-";
            table.addCell(new Phrase(contaDestino, NORMAL_FONT));
        }
    }

    public byte[] gerarRelatorioExcel(java.util.List<Transacao> transacoes, String titulo) {
        // Implementação para Excel usando Apache POI
        // Retornar array de bytes do arquivo Excel
        log.info("Gerando relatório Excel com {} transações", transacoes.size());
        return new byte[0]; // Implementação placeholder
    }

    public byte[] gerarRelatorioCsv(java.util.List<Transacao> transacoes) {
        StringBuilder csv = new StringBuilder();

        // Cabeçalho
        csv.append("Data,Tipo,Valor,Conta Origem,Conta Destino\n");

        // Dados
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Transacao transacao : transacoes) {
            csv.append(transacao.getDataTransacao().format(formatter)).append(",");
            csv.append(transacao.getTipoTransacao().getDescricao()).append(",");
            csv.append(transacao.getValor().toString()).append(",");
            csv.append(transacao.getContaOrigem().getNumeroConta()).append(",");

            String contaDestino = transacao.getContaDestino() != null ?
                    transacao.getContaDestino().getNumeroConta() : "";
            csv.append(contaDestino).append("\n");
        }

        log.info("Relatório CSV gerado com {} transações", transacoes.size());
        return csv.toString().getBytes();
    }

    public byte[] gerarRelatorio(FormatoRelatorio formato, java.util.List<Transacao> transacoes, String titulo) {
        switch (formato) {
            case PDF:
                return gerarRelatorioPdf(transacoes, titulo);
            case EXCEL:
                return gerarRelatorioExcel(transacoes, titulo);
            case CSV:
                return gerarRelatorioCsv(transacoes);
            default:
                throw new IllegalArgumentException("Formato de relatório não suportado: " + formato);
        }
    }
}