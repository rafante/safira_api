package br.com.eliteconsult.financeiro

import base.CompensacaoItemFinanceiroBase

class CompensacaoItemReceber extends CompensacaoItemFinanceiroBase {
    static belongsTo = [itemContaReceber: ItemContaReceber, movimentoFinanceiro: MovimentoFinanceiro, cheque: Cheque]

    ItemContaReceber getParent() { return itemContaReceber }
}