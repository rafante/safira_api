package br.com.eliteconsult.materiais

import br.com.eliteconsult.cadastros.ParceiroNegocios

public class MaterialLote {

    String lote
    Date validade_lote
    BigDecimal estoque = 0

    static defaultValueField = "lote"
    static defaultFilterFields = "lote;fornecedor"
    static defaultAutoCompleteFields = "lote"

    static belongsTo = [material: Material, fornecedor: ParceiroNegocios]

    static constraints = {
        material()
        fornecedor()
        lote()
        validade_lote()
    }

    String toString() {
        return "Lote: " + this.lote + "| Estoque: " + this.estoque + " | Forn:" + fornecedor.toString() + " | Venc:" + this.validade_lote?.format("dd/MM/yy")
    }

}