package br.com.eliteconsult.cadastros

class Setor{

    String descricao

    static defaultAutoCompleteFields = "descricao"

    static constraints = {
        descricao size:  0..60
    }

    @Override
    String toString() {
        return id + " - " + descricao
    }
}
