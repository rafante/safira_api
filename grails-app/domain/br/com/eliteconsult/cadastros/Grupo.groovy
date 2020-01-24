package br.com.eliteconsult.cadastros

class Grupo {

    static displayFields = ["descricao"]

    String descricao

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"

    String toString() {
        return descricao
    }
}
