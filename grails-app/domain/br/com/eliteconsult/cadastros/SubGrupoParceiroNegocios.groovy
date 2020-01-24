package br.com.eliteconsult.cadastros

class SubGrupoParceiroNegocios {

    String descricao

    static belongsTo = [grupo_parceiro: GrupoParceiroNegocios]

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"

    String toString() {
        return descricao
    }
}
