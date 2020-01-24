package br.com.eliteconsult.cadastros

class Setor{

    String descricao

    static defaultAutoCompleteFields = "descricao"

    @Override
    String toString() {
        return id + " - " + descricao
    }
}
