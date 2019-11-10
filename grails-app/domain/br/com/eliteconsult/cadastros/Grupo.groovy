package br.com.eliteconsult.cadastros

class Grupo {

    static displayFields = ["descricao"]

    String descricao

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"
    
    static constraints = {
        descricao(size: 0..60)
    }

    String toString() {
        return descricao
    }
}
