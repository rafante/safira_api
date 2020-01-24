package br.com.eliteconsult.financeiro

import br.com.eliteconsult.cadastros.FormaPagamento
import br.com.eliteconsult.cadastros.HistoricoPadrao
import br.com.eliteconsult.cadastros.TipoDocumento

class MovimentoFinanceiro {
    String descricao
    Boolean conciliado = false
    Boolean eEstorno = false
    Date dataDocumento
    Date dataEmissao
    TipoMovimentoFinanceiro debito_credito
    String numeroDocumento
    PlanoContas planoContas
    ContaCorrente contaCorrente
    TipoDocumento tipoDocumento
    FormaPagamento formaPagamento
    BigDecimal valor
    Boolean estornado = false

    static belongsTo = [historicoPadrao           : HistoricoPadrao,
            movimentoFinanceiroEstorno: MovimentoFinanceiro]

    static defaultValueField = "descricao"
    static defaultFilterFields = "dataDocumento;dataEmissao;contaCorrente;planoContas;numeroDocumento"
    static defaultAutoCompleteFields = "descricao"
//    static transients = Persistente.transients + ["valorCredito", "valorDebito"]

    static constraints = {
        movimentoFinanceiroEstorno(cascade: 'all-delete-orphan')
    }

    def beforeValidate() {
        if (this.debito_credito) {
            this.valor = Math.abs(this.valor) * (this.debito_credito == TipoMovimentoFinanceiro.DEBITO ? -1 : 1)
        } else {
            this.debito_credito = this.valor < 0 ? TipoMovimentoFinanceiro.DEBITO : TipoMovimentoFinanceiro.CREDITO
        }
    }

    String toString() {
        return id.toString()
    }

    def estornar() {
        // Copia as propriedades do objeto atual
        def propEstorno = this.properties
        propEstorno.id = null

        // Cria o objeto e ajusta seus atributos para o estorno
        this.movimentoFinanceiroEstorno = new MovimentoFinanceiro(propEstorno)
        this.movimentoFinanceiroEstorno.descricao = this.movimentoFinanceiroEstorno.descricao?.replace("Compensação", "Estorno")
        this.movimentoFinanceiroEstorno.debito_credito = this.debito_credito == TipoMovimentoFinanceiro.DEBITO ? TipoMovimentoFinanceiro.CREDITO : TipoMovimentoFinanceiro.DEBITO
        this.movimentoFinanceiroEstorno.eEstorno = true
        this.movimentoFinanceiroEstorno.save()

        if (this.movimentoFinanceiroEstorno.hasErrors()) {
            for (err in this.movimentoFinanceiroEstorno.errors) {
                return err.fieldError
            }
        }

//        this.valor = this.valor * -1
        this.estornado = true
        this.save()

        if (this.hasErrors()) {
            for (err in this.errors) {
                return err.fieldError
            }
        }
    }

    BigDecimal getValorCredito() {
        return (this.debito_credito == TipoMovimentoFinanceiro.CREDITO ? valor : null)    }

    BigDecimal getValorDebito() {
        return (this.debito_credito == TipoMovimentoFinanceiro.DEBITO ? valor : null)    }
}

enum TipoMovimentoFinanceiro {

    CREDITO("C"),
    DEBITO("D")

    final String id

    private TipoMovimentoFinanceiro(String id) {
        this.id = id
    }

    String toString() {
        name()
    }

    String value() {
        this.id
    }
}
