package br.com.eliteconsult.cadastros

public class FormaPagamento {

    String descricao
    Boolean controle_cheque

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"

    String toString() {
        return descricao
    }

}