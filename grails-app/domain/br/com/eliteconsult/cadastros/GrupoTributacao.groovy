package br.com.eliteconsult.cadastros

class GrupoTributacao {

    String descricao

    static constraints = {
        descricao(size: 0..60)
    }

    String toString() {
        return descricao
    }
}
