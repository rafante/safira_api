package br.com.eliteconsult.cadastros

public class ItemPrazoPagamento {

    BigDecimal percentual
    Integer prazo

    static belongsTo = [prazoPagamento: PrazoPagamento]

    static constraints = {
        percentual(scale: 2)

        /**
         * N�mero de dias de prazo.
         */
        prazo()
    }

    String toString() {
        return prazo
    }
}