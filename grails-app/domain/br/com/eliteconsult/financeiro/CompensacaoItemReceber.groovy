package br.com.eliteconsult.financeiro

import br.com.eliteconsult.Cheque
import br.com.eliteconsult.MovimentoFinanceiro

class CompensacaoItemReceber {
    static belongsTo = [itemContaReceber: ItemContaReceber, movimentoFinanceiro: MovimentoFinanceiro, cheque: Cheque]

    ItemContaReceber getParent() { return itemContaReceber }
}