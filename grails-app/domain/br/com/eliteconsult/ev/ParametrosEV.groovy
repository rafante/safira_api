package br.com.eliteconsult.ev

import br.com.eliteconsult.cadastros.CentroCusto
import br.com.eliteconsult.financeiro.ContaCorrente
import br.com.eliteconsult.financeiro.PlanoContas

class ParametrosEV {

    String caixaEVEncoding = "ISO-8859-1"
    String examesEncoding = "UTF-8"
    String nfeEncoding = "UTF-8"
    static belongsTo = [planoContas: PlanoContas, contaCorrente: ContaCorrente, centroCusto: CentroCusto]
}
