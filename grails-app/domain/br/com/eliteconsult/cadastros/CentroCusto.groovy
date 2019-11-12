package br.com.eliteconsult.cadastros

class CentroCusto {

    String codigo
    String descricao

    static belongsTo = [centroCusto: CentroCusto]

    static hasMany = [filhos: CentroCusto]
    static transients = ['temFilhos']

    static constraints = {
        codigo()
        descricao(size: 0..60)
    }

    Boolean getTemFilhos(){
        return filhos?.size()
    }

    String toString() {

        CentroCusto almoxarifado = new CentroCusto()
        CentroCusto recepcao = new CentroCusto()


        return codigo + " - " + descricao
    }
}