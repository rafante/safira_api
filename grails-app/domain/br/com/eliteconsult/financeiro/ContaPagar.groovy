package br.com.eliteconsult.financeiro

import base.FinanceiroBase
import base.TipoFinanceiro

public class ContaPagar extends FinanceiroBase{
    Boolean boletoRecebido

    static hasMany = [itensContaPagar: ItemContaPagar]

    static defaultFilterFields = "parceiroNegocios;dataEmissao;dataDocumento;documento"

    def getItens() { return this.itensContaPagar }

    TipoFinanceiro getTipoFinanceiro() { return TipoFinanceiro.PAGAR }

    def getParceiroShort(){
        def nome = parceiroNegocios.nome;
        return nome.size() >= 21 ? nome.substring(0,21) : nome
    }

    static constraints = {
        itensContaPagar(cascade: 'all-delete-orphan')
    }
}