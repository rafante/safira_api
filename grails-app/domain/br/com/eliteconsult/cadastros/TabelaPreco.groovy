package br.com.eliteconsult.cadastros

public class TabelaPreco {

    String descricao
    BigDecimal margem_sugerida
    BigDecimal margem_minima

    static defaultValueField = "descricao"

    String toString() {
        return descricao
    }

}