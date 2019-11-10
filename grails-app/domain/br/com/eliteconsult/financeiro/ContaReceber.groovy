package br.com.eliteconsult.financeiro

import base.TipoFinanceiro

class ContaReceber { //As propriedades da ContaReceber t�o dentro da classe pai FinanceiroBase
    //ent�o vamos l� na FinanceiroBase
    static hasMany = [itensContaReceber: ItemContaReceber]

    static defaultFilterFields = "parceiroNegocios;dataEmissao;dataDocumento;documento"

    static transients = ['codigoDeBarras']

    def getItens() { return this.itensContaReceber }

    TipoFinanceiro getTipoFinanceiro() { return TipoFinanceiro.RECEBER }

    static constraints = {
        empresa()
        referencia()
        parceiroNegocios(asgDefaultFilter: [cliente: true])
        centroCusto()
        planoContas(validator: { val, obj->
            if(obj?.planoContas?.filhos?.size())
                return ['financeiro.mensagens.PlanoContasPai']
            if(obj?.planoContas?.natureza == null)
                return ['financeiro.mensagens.natureza.invalida']
            if(obj?.planoContas?.natureza == Natureza.DESPESA)
                return ['financeiro.mensagens.natureza.nao.permitida']
        })
        descricao(blank: false, nullable: false)
        dataEmissao()
        dataDocumento()
        itensContaReceber(cascade: 'all-delete-orphan')
        tipoDocumento()
        historicoPadrao()
    }

    static mapping = {
        descricao type: 'text'
    }

    def getCodigoDeBarras(ContaCorrente conta){
        def contaUsada = conta ?: this.contaCorrente
        if(!conta)
            return null

        //código do banco (353 - Santander antigo)
        def _1_3 = contaUsada.banco.codigo
        //código da moeda (9 - BRL)
        def _4_4 = '9'
        //dígito verificador
        def _5_5 = ''
        //fator de vencimento
        long fator = dataDocumento.time - new Date(1997,10,7).time
        fator /= 1000 //segundos
        fator /= 60 // minutos
        fator /= 60 //horas
        fator /= 24 //dias
        fator += 1
        def _6_9 = fator
        //valor nominal
        def _10_19 = valorTotal.toString().replaceAll('\\.', '').padLeft(10,'0')
        //fixo 9
        def _20_20 = '9'
        //código do beneficiário padrão Santander
        def _21_27 = parceiroNegocios.codigoSantander
        //nosso numero com DV
        def _28_40 = contaUsada.nossoNumero
        //IOF – Seguradoras (Se 7% informar 7. Limitado a 9%)
        //Demais clientes usar 0 (zero)
        def _41_41 = '0'
        //Tipo de Modalidade Carteira 101-Cobrança Rápida COM Registro
        def _42_44 = '101'
        String codV = _1_3 + _4_4 + _6_9 + _10_19 + _20_20 + _21_27 + _28_40 + _41_41 + _42_44
        _5_5 = calculaDigitoValidador(codV)
        return _1_3 + _4_4 + _5_5 + _6_9 + _10_19 + _20_20 + _21_27 + _28_40 + _41_41 + _42_44
    }

    def calculaDigitoValidador(String codigoDeBarras){
        char[] revertido = codigoDeBarras.toCharArray().toList().reverse().toArray()
        int fatorMultiplicador = 2
        int total = 0
        int indice = 1
        revertido.each { caractere ->
            int num = Integer.parseInt(caractere.toString())
            def somar = num * fatorMultiplicador
            total += somar
            if(indice >= 13 && indice <= 17)
                somar = 0

            fatorMultiplicador++
            if(fatorMultiplicador > 9)
                fatorMultiplicador = 2
            indice++
        }
        total *= 10
        return total %= 11
    }
}