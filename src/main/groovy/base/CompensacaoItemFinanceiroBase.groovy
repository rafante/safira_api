package base

import br.com.eliteconsult.cadastros.FormaPagamento
import br.com.eliteconsult.financeiro.Cheque
import br.com.eliteconsult.financeiro.ContaCorrente
import br.com.eliteconsult.financeiro.MovimentoFinanceiro
import grails.validation.ValidationException

abstract class CompensacaoItemFinanceiroBase {
    Boolean estornado
    BigDecimal valor
    ContaCorrente contaCorrente
    FormaPagamento formaPagamento
    String justificativaEstorno
    Date data

    static belongsTo = [movimentoFinanceiro: MovimentoFinanceiro, cheque: Cheque]

    static constraints = {
        estornado(default: false)
        movimentoFinanceiro(cascade: 'all-delete-orphan')
        cheque(cascade: 'all-delete-orphan')
    }


    ItemFinanceiroBase getParent() {}

    def estornar(String justificativa) {
        if (!justificativa) {
            throw new ValidationException('compensacaoItemFinanceiroBase.justificativa.empty')
        } else if (this.estornado) {
            throw new ValidationException('message.already.refunded')
        } else {
            ItemFinanceiroBase itemFinanceiroBase = this.getParent()
            this.estornado = true
            this.justificativaEstorno = justificativa
            this.movimentoFinanceiro.estornar()

            // Verifica os erros do MovimentoFinanceiro
            if (this.movimentoFinanceiro.movimentoFinanceiroEstorno.hasErrors()) {
                for (err in this.movimentoFinanceiro.movimentoFinanceiroEstorno.errors) {
                    return err.fieldError
                }
            }

            // Verifica os erros do MovimentoFinanceiro
            if (this.movimentoFinanceiro.hasErrors()) {
                for (err in this.movimentoFinanceiro.errors) {
                    return err.fieldError
                }
            }

            // Salva o CompensacaoItem se tiver salvado o MovimentoFinanceiro
            if (!this.save()) {
                for (err in this.errors) {
                    return err.fieldError
                }
            }

            // Atualiza o flag de compensado completamente
            itemFinanceiroBase.refreshCompensadoCompletamente()

            // Salva o CompensacaoItem se tiver salvado tudo
            if (!itemFinanceiroBase.save()) {
                for (err in itemFinanceiroBase.errors) {
                    return err.fieldError
                }
            }
        }

        return 'message.success.refund'
    }
}
