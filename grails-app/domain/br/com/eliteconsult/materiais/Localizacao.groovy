package br.com.eliteconsult.materiais

class Localizacao {

    String descricao

    static constraints = {
        descricao(size: 0..60)
    }

    String toString() {
        return descricao
    }
}
