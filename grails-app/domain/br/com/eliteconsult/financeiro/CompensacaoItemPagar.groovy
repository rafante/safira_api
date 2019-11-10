package br.com.eliteconsult.financeiro

import br.com.eliteconsult.Cheque

class CompensacaoItemPagar {
    static belongsTo = [itemContaPagar: ItemContaPagar, movimentoFinanceiro: MovimentoFinanceiro, cheque: Cheque]

    ItemContaPagar getParent() { return itemContaPagar }
    static mapping = {
        cheque cascade: 'all-delete-orphan'
    }
}