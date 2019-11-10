package br.com.eliteconsult.cadastros

import br.com.eliteconsult.financeiro.ContaCorrente
import br.com.eliteconsult.financeiro.PlanoContas

class HistoricoPadrao {

    static displayFields = ["descricao"]

    String descricao
    PlanoContas planoContas
    CentroCusto centroCusto
    ContaCorrente contaCorrente

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"
    static defaultAutoCompleteFields = "descricao"

    String toString() {
        return descricao
    }
}
