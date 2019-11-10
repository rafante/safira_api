package br.com.eliteconsult.financeiro

import base.ItemFinanceiroBase

class ItemContaReceber extends ItemFinanceiroBase {
    static tipoMovimentoFinanceiro = TipoMovimentoFinanceiro.CREDITO

    static defaultFilterFields = "contaReceber.parceiroNegocios;dataVencimento;contaReceber.dataEmissao;compensadoCompletamente"
    static defaultFilterFieldsExtra = [[name: 'agruparParceiro', type: Boolean], [name: 'agruparData', type: Boolean]]

    static belongsTo = [contaReceber: ContaReceber] //veja belongsTo significa pertencente a ou seja ele tem uma propriedade chamada contaReceber
    //que nada mais � do que uma referencia ao "dono" deste item. O dono � uma ContaReceber portanto vamos l� na ContaReceber

    static hasMany = [compensacaoItemReceber: CompensacaoItemReceber, prorrogacaoItemReceber: ProrrogacaoItemReceber]

    static constraints = {
        compensacaoItemReceber(cascade: "all-delete-orphan")
    }

    ContaReceber getParent() { return contaReceber }

//    PersistentSet getCompensacoes() { return compensacaoItemReceber }

//    PersistentSet getProrrogacoes() { return prorrogacaoItemReceber }
}