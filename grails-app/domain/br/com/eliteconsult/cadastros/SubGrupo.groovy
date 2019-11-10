package br.com.eliteconsult.cadastros

class SubGrupo {

    String descricao

    static belongsTo = [grupo: Grupo]

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"
    
    static constraints = {
        descricao(size: 0..60)
        grupo()
    }

    String toString() {
        return descricao
    }
}
