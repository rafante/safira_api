package br.com.eliteconsult.financeiro

import br.com.eliteconsult.cadastros.HistoricoPadrao

public class TransferenciaConta {
    Date data
    String descricao
    HistoricoPadrao historicoPadrao
    ContaCorrente contaCorrenteDestino
    ContaCorrente contaCorrenteOrigem
    BigDecimal valor
    Boolean estornado

    static hasMany = [
            movimentoFinanceiro: MovimentoFinanceiro
    ]

    static constraints = {
        movimentoFinanceiro(cascade: 'all-delete-orphan')
    }

    def estornar() {
        for (MovimentoFinanceiro mov in movimentoFinanceiro) {
            mov.estornar()
        }

        this.estornado = true
    }
}