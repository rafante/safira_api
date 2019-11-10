package br.com.eliteconsult.cadastros

class SubGrupoParceiroNegocios {

    String descricao

    static belongsTo = [grupo_parceiro: GrupoParceiroNegocios]

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"
    
    static constraints = {
        descricao(size: 0..60)
        grupo_parceiro()
    }

    String toString() {
        return descricao
    }
}
