package br.com.eliteconsult.cadastros

class SubGrupo {

    String descricao

    static belongsTo = [grupo: Grupo]

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"

    String toString() {
        return descricao
    }
}
