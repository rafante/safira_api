package br.com.eliteconsult.ev

import br.com.eliteconsult.ContaCorrente
import br.com.eliteconsult.PlanoContas
import br.com.eliteconsult.cadastros.CentroCusto

class ParametrosEV {

    String caixaEVEncoding = "ISO-8859-1"
    String examesEncoding = "UTF-8"
    String nfeEncoding = "UTF-8"
    static belongsTo = [planoContas: PlanoContas, contaCorrente: ContaCorrente, centroCusto: CentroCusto]

    static constraints = {
        planoContas(nullable: true)
        contaCorrente(nullable: true)
        centroCusto(nullable: true)
    }
}
