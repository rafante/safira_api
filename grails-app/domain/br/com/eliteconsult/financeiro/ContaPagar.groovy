package br.com.eliteconsult.financeiro

import base.TipoFinanceiro

public class ContaPagar {
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
        empresa()
        referencia()
        parceiroNegocios(asgDefaultFilter: [fornecedor: true])
        centroCusto()
        planoContas(validator: { val, obj->
            if(obj?.planoContas?.filhos?.size())
                return ['financeiro.mensagens.PlanoContasPai']
            if(obj?.planoContas?.natureza == null)
                return ['financeiro.mensagens.natureza.invalida']
            if(obj?.planoContas?.natureza == Natureza.RECEITA)
                return ['financeiro.mensagens.natureza.nao.permitida']
        })
        descricao(blank: false, nullable: false)
        dataEmissao()
        dataDocumento()
        itensContaPagar(cascade: 'all-delete-orphan')
        tipoDocumento()
        historicoPadrao()
        boletoRecebido()
    }
}