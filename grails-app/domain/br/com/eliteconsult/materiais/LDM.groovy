package br.com.eliteconsult.materiais

public class LDM {

    BigDecimal quantidade

    static defaultFilterFields = "material;material.grupo;material.sub_grupo;material.localizacao"

    static belongsTo = [material: Material, material_composicao: Material]

//    static transients = Persistente.transients + ["custo", "custoTotal"]

    String toString() {
        return material?.toString() + " - " +
                quantidade
    }

    BigDecimal getCusto() {
        return material_composicao?.custo_total
    }

    BigDecimal getCustoTotal() {
        return material_composicao?.custo_total ? material_composicao?.custo_total * quantidade : 0
    }

    BigDecimal getPesoTotal() {
        return material_composicao?.peso ? material_composicao.peso * quantidade : 0
    }

}