package br.com.eliteconsult.materiais

public class ItemSaida {

    Integer item
    BigDecimal quantidade
    BigDecimal valor
    BigDecimal total

    static transients = ["total"]

    static belongsTo = [
            saidaMaterial: SaidaMaterial,
            material: Material,
            materialLote: MaterialLote,
            unidade_medida: UnidadeMedida]

    static hasMany = [movimento_material: MovimentoMaterial]

    static constraints = {
        item()
        material()
        quantidade(scale: 2)

        /**
         * Tamanho 5
         */
        unidade_medida()
        valor(scale: 2)
    }

    def beforeInsert = {
        def maxitem = withCriteria {
            projections { max("item") }
            eq("saidaMaterial", saidaMaterial)
        }[0]

        if (maxitem) this.item = maxitem + 1
        else this.item = 1
    }

    void geraMovimentoMaterial(int modificador, String controller, String action, centroCusto) {
        def movimento = material.geraMovimentoMaterial(this.materialLote, (quantidade * modificador), null, controller, action, id, centroCusto)
        movimento_material.add(movimento)
    }

    void geraMovimentoMaterial(int modificador, String controller, String action) {
        geraMovimentoMaterial(modificador, controller, action, null)
    }

    BigDecimal getTotal() {
        return Utils.ToDouble(valor) * Utils.ToDouble(quantidade)
    }

    String toString() {
        return (item + " - " +
                material?.descricao + " - " +
                quantidade + " - " +
                valor).replace("null - ", "").replace("null", "")
    }
}