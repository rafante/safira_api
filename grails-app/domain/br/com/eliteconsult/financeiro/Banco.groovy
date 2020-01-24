package br.com.eliteconsult.financeiro

class Banco {

    String descricao
    Integer codigo

    static defaultAutoCompleteFields = "codigo;descricao"

    String toString(){
        descricao
    }
}
