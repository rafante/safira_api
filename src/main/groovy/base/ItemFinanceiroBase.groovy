package base

import br.com.eliteconsult.cadastros.FormaPagamento
import br.com.eliteconsult.financeiro.Cheque
import br.com.eliteconsult.financeiro.ContaCorrente
import br.com.eliteconsult.financeiro.MovimentoFinanceiro
import grails.validation.ValidationException

abstract class ItemFinanceiroBase {
    Integer item
    Date dataVencimento
    BigDecimal valor
    BigDecimal multa
    BigDecimal juros
    BigDecimal descontos
    Integer diasVencimento
    Boolean compensadoCompletamente = false
    FormaPagamento formaPagamento

//    static transients = Persistente.transients + ["valorCompensado", "diasVencimento", "valorDia"]

    protected void beforeValidate() {
        fillNumItem()
    }

    public void fillNumItem() {
        if (!this.item) this.item = getMaxNumItem()
    }

    private Integer getMaxNumItem() {
        def itensOrdenados = this.getParent().getItens()?.sort { a, b -> b?.item <=> a?.item }
        Integer max = itensOrdenados.first()?.item ?: 0
        return max + 1
    }

    String toString() {
        return dataVencimento.toString() + " - " + valor
    }

    FinanceiroBase getParent() {}

    String getParentName() {
        return "conta" + getEntityName()
    }

//    PersistentSet getCompensacoes() {}
//
//    PersistentSet getProrrogacoes() {}

    BigDecimal getValorDia() {
        if (this.compensadoCompletamente) return 0
        return getValorRestante()
    }

    BigDecimal getValorRestante() {
        //Valor do dia = valor da parcela + multa + (valor da parcela * (valor dos juros / 100) * dias em atraso) - valor compensado
        def dias_atraso = (this.dataVencimento && this.dataVencimento < new Date()) ? new Date() - this.dataVencimento : 0

        this.valor = this.valor ?: 0;
        this.multa = this.multa ?: 0;
        this.juros = this.juros ?: 0;

        BigDecimal calcValor = this.valor ?: 0;
        BigDecimal calcCompensado = this.getValorCompensado() ?: 0;
        BigDecimal calcMulta = (dias_atraso > 0) ? this.multa : 0;
        BigDecimal calcJuros = (dias_atraso > 0) ? (this.valor * this.juros / 100 * dias_atraso) : 0;

        return (calcValor + calcMulta + calcJuros - calcCompensado).setScale(2, RoundingMode.HALF_EVEN)
    }

    BigDecimal getValorCompensado() {
        def ret = 0
        for (CompensacaoItemFinanceiroBase it in getCompensacoes()) {
            if (!it.estornado) ret += it.valor ?: 0
        }

        return ret
    }

    Integer getDiasVencimento() {
        def dias = (this.dataVencimento && getParent().dataEmissao) ? this.dataVencimento - getParent()?.dataEmissao : 0

        return dias
    }

    String getEntityName() {
        return this.tipoMovimentoFinanceiro == TipoMovimentoFinanceiro.DEBITO ? 'Pagar' : 'Receber'
    }

    String getDocumentoParcela() {
        return getParent()?.id?.toString()?.padLeft(6, "0") + item + 123
    }

    def compensar(Date data, FormaPagamento formaPagamento, ContaCorrente contaCorrente) {
        compensar(this.getValorRestante(), data, formaPagamento, contaCorrente, null)
    }

    def compensar(BigDecimal valor, Date data, FormaPagamento formaPagamento, ContaCorrente contaCorrente, Cheque cheque) {
        if (valor > this.getValorRestante() || this.compensadoCompletamente) throw new ValidationException("itemFinanceiroBase.compensation.exceeds")

        def entity = getEntityName()

        // Objeto COMPENSAÇÃO
        CompensacaoItemFinanceiroBase compensacaoItemFinanceiroBase = this.class.classLoader.loadClass('com.br.asgardtecnologia.erp.financeiro.CompensacaoItem' + entity).newInstance()
        compensacaoItemFinanceiroBase.valor = valor
        compensacaoItemFinanceiroBase.contaCorrente = contaCorrente
        compensacaoItemFinanceiroBase.formaPagamento = formaPagamento
        compensacaoItemFinanceiroBase.data = data

        if (cheque) {
            compensacaoItemFinanceiroBase.cheque = cheque
        }

        compensacaoItemFinanceiroBase['itemConta' + entity] = this

        // Objeto MOVIMENTO
        def movimentoFinanceiroInstance = new MovimentoFinanceiro();
        movimentoFinanceiroInstance.dataDocumento = data
        movimentoFinanceiroInstance.dataEmissao = data
//        movimentoFinanceiroInstance.planoContas = getParent().planoContas ?: contaCaixa
        movimentoFinanceiroInstance.contaCorrente = contaCorrente
        movimentoFinanceiroInstance.numeroDocumento = getParent().documento
        movimentoFinanceiroInstance.debito_credito = tipoMovimentoFinanceiro
        movimentoFinanceiroInstance.formaPagamento = formaPagamento
        movimentoFinanceiroInstance.valor = compensacaoItemFinanceiroBase.valor *
                (movimentoFinanceiroInstance.debito_credito.equals(TipoMovimentoFinanceiro.DEBITO) ? -1 : 1)
        movimentoFinanceiroInstance.descricao = "Compensação " + this.getEntityName() + "-" + this.getParent().parceiroNegocios + "-" + this.getParent().id + "/" + this.item
        movimentoFinanceiroInstance.tipoDocumento = this.getParent().tipoDocumento
        movimentoFinanceiroInstance.historicoPadrao = this.getParent().historicoPadrao

        // Salva o MovimentoFinanceiro
        if (!movimentoFinanceiroInstance.save()) {
            movimentoFinanceiroInstance.errors.each {
                return it.fieldError
            }
        } else {
            compensacaoItemFinanceiroBase.movimentoFinanceiro = movimentoFinanceiroInstance

            if (compensacaoItemFinanceiroBase.cheque) {
                compensacaoItemFinanceiroBase.cheque.save()
            }

            // Salva o CompensacaoItem se tiver salvo o MovimentoFinanceiro
            if (!compensacaoItemFinanceiroBase.save()) {
                compensacaoItemFinanceiroBase.errors.each {
                    return it.fieldError
                }
            }

        }

        this."addToCompensacaoItem${entity}"(compensacaoItemFinanceiroBase)
        this.refreshCompensadoCompletamente()

        // Salva o ItemFinanceiro
        if (!this.save()) {
            this.errors.each {
                return it.fieldError
            }
        } else {
            return 'compensacaoItemFinanceiroBase.success'
        }
    }

    def refreshCompensadoCompletamente() {
        BigDecimal valorRestante = getValorRestante()
        this.compensadoCompletamente = (valorRestante == 0)
    }

}