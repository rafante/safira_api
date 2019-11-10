package br.com.eliteconsult.financeiro

class Banco {

    String descricao
    Integer codigo

    static defaultAutoCompleteFields = "codigo;descricao"

    static constraints = {
    }

    String toString(){
        descricao
    }
}
