package br.com.eliteconsult.ev

import br.com.eliteconsult.materiais.Material

class ItemExame{

    Integer item
    static belongsTo = [exame: Exame, material: Material]

    static defaultFilterFields = "material;exame"
    static defaultAutoCompleteFields = "material"

    static constraints = {
        material(nullable: false, unique: ['exame'])
    }

    @Override
    String toString() {
        return material.toString()
    }
}
