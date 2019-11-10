package br.com.eliteconsult.cadastros

public class FormaPagamento {

    String descricao
    Boolean controle_cheque

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"

    static constraints = {
        /**
         * Tamanho 60.
         */
        descricao(size: 0..60, unique: true)

        controle_cheque()
    }

    String toString() {
        return descricao
    }

}