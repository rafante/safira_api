package br.com.eliteconsult.materiais

public class Recipiente {

    Long codEv
    String descricao

    static defaultAutoCompleteFields = "codEv;descricao"

    static constraints = {
        codEv unique: true
    }

    @Override
    String toString() {
        return id + " - " + descricao
    }
}
