package br.com.eliteconsult.financeiro

class Cheque {

    String numero
    ContaCorrente contaCorrente
    BigDecimal valor

    @Override
    String toString() {
        return this.numero + " | " + this.contaCorrente?.toString()
    }
}
