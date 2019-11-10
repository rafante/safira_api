package br.com.eliteconsult.cadastros

public class NCM {

    String codigo
    String descricao

    static constraints = {
        codigo()
        descricao()
    }

    String toString() {
        return codigo + " - " + descricao
    }

}