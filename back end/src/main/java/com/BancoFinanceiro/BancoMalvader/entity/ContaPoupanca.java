@Entity
@Table(name = "contas_poupanca")
@PrimaryKeyJoinColumn(name = "conta_id")
@Getter @Setter
@NoArgsConstructor
public class ContaPoupanca extends Conta {

    @Column(precision = 5, scale = 4)
    private BigDecimal taxaRendimento = BigDecimal.valueOf(0.005);

    private Integer diaAniversario;

    public ContaPoupanca(BigDecimal taxaRendimento, Integer diaAniversario) {
        super();
        this.setTipoConta(TipoConta.POUPANCA);
        this.taxaRendimento = taxaRendimento;
        this.diaAniversario = diaAniversario;
    }

    @Override
    protected String gerarNumeroConta() {
        return "CP" + System.currentTimeMillis();
    }

    public void aplicarRendimento() {
        BigDecimal rendimento = getSaldoConta().multiply(taxaRendimento);
        depositar(rendimento);
    }
}
