package br.com.eliteconsult.financeiro

class TipoConta {

    String descricao

    static constraints = {
    }

    @Override
    String toString() {
        return descricao
    }
}