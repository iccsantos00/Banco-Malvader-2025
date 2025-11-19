@Entity
@Table(name = "contas_investimento")
@PrimaryKeyJoinColumn(name = "conta_id")
@Getter @Setter
@NoArgsConstructor
public class ContaInvestimento extends Conta {

    @Column(precision = 5, scale = 4)
    private BigDecimal taxaAdministracao = BigDecimal.valueOf(0.01);

    @Column(precision = 15, scale = 2)
    private BigDecimal valorAplicado = BigDecimal.ZERO;

    public ContaInvestimento(BigDecimal taxaAdministracao) {
        super();
        this.setTipoConta(TipoConta.INVESTIMENTO);
        this.taxaAdministracao = taxaAdministracao;
    }

    @Override
    protected String gerarNumeroConta() {
        return "CI" + System.currentTimeMillis();
    }

    public void aplicarInvestimento(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor deve ser positivo");

        if (getSaldoConta().compareTo(valor) < 0)
            throw new IllegalArgumentException("Saldo insuficiente");

        valorAplicado = valorAplicado.add(valor);
        sacar(valor);
    }

    public BigDecimal getValorTotal() {
        return getSaldoConta().add(valorAplicado);
    }
}
