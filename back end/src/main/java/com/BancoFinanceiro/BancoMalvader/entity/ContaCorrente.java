@Entity
@Table(name = "contas_corrente")
@PrimaryKeyJoinColumn(name = "conta_id")
@Getter @Setter
@NoArgsConstructor
public class ContaCorrente extends Conta {

    @Column(precision = 15, scale = 2)
    private BigDecimal limiteChequeEspecial = BigDecimal.ZERO;

    @Column(precision = 15, scale = 2)
    private BigDecimal tarifaMensal = BigDecimal.valueOf(15.00);

    public ContaCorrente(BigDecimal limiteChequeEspecial, BigDecimal tarifaMensal) {
        super();
        this.setTipoConta(TipoConta.CORRENTE);
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.tarifaMensal = tarifaMensal;
    }

    @Override
    protected String gerarNumeroConta() {
        return "CC" + System.currentTimeMillis();
    }

    public BigDecimal getSaldoDisponivel() {
        return getSaldoConta().add(limiteChequeEspecial);
    }

    public boolean possuiChequeEspecial() {
        return limiteChequeEspecial.compareTo(BigDecimal.ZERO) > 0;
    }

    public void aplicarTarifaMensal() {
        BigDecimal saldoAtual = getSaldoConta();

        if (saldoAtual.compareTo(tarifaMensal) >= 0) {
            sacar(tarifaMensal);
        } else {
            modificarSaldo(saldoAtual.subtract(tarifaMensal));
        }
    }

    public void sacarComChequeEspecial(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor deve ser positivo");

        if (getSaldoDisponivel().compareTo(valor) < 0)
            throw new IllegalArgumentException("Saldo insuficiente mesmo com cheque especial");

        modificarSaldo(getSaldoConta().subtract(valor));
    }

    public BigDecimal getLimiteDisponivel() {
        BigDecimal saldoAtual = getSaldoConta();
        return saldoAtual.compareTo(BigDecimal.ZERO) >= 0
                ? limiteChequeEspecial
                : limiteChequeEspecial.add(saldoAtual);
    }
}
