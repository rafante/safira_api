package br.com.eliteconsult.cadastros

public class ItemPrazoPagamento {

    BigDecimal percentual
    Integer prazo

    static belongsTo = [prazoPagamento: PrazoPagamento]

    String toString() {
        return prazo
    }
}