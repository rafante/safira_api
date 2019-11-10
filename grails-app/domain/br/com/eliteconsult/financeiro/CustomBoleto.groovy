package br.com.eliteconsult.financeiro

import br.com.eliteconsult.cadastros.Empresa
import br.com.eliteconsult.cadastros.ParceiroNegocios

class CustomBoleto {

    ParceiroNegocios cliente
    Empresa cedente
    ContaCorrente contaCorrente
    String numeroDocumento
    String nossoNumero
    Date vencimento
    Date data
    BigDecimal valor
//    TipoDeTitulo tipoDeTitulo
//    BancosSuportados banco

    static constraints = {
    }
}
