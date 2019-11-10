package br.com.eliteconsult.cadastros

public class TabelaPreco {

    String descricao
    BigDecimal margem_sugerida
    BigDecimal margem_minima

    static defaultValueField = "descricao"

    static constraints = {
        /**
         * Tamanho 60
         */
        descricao()
    }

    String toString() {
        return descricao
    }

}