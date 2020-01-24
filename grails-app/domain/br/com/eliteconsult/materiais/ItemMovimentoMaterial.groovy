package br.com.eliteconsult.materiais

public class ItemMovimentoMaterial {

    Integer item
    BigDecimal quantidade = 0
    BigDecimal valor = 0
    BigDecimal total = 0
    String lote
    Date validade_lote

    static defaultFilterFields = "material;quantidade;unidade_medida;movimentoMaterial.data_movimento;valor;movimentoMaterial.centroCusto"

    static transients = ["total", "entrada_saida, qtdRelatorio"]

    static belongsTo = [
            movimentoMaterial: MovimentoMaterial,
            material         : Material,
            materialLote     : MaterialLote,
            unidade_medida   : UnidadeMedida]

    def beforeInsert = {
        def maxitem = withCriteria {
            projections { max("item") }
            eq("movimentoMaterial", movimentoMaterial)
        }[0]

        if (maxitem) this.item = maxitem + 1
        else this.item = 1
    }

    TipoMovimentoMaterial getEntrada_saida(){
        return movimentoMaterial.entrada_saida
    }

    void geraMovimentoMaterial(int modificador, String controller, String action, centroCusto) {
        material.geraMovimentoMaterial(this.materialLote, (quantidade * modificador), null, controller, action, id, centroCusto)
    }

    void geraMovimentoMaterial(int modificador, String controller, String action) {
        geraMovimentoMaterial(modificador, controller, action, null)
    }

    void geraMovimento(modificador){
        material.geraMovimento(materialLote, (modificador * quantidade), unidade_medida, movimentoMaterial.centroCusto)
    }

    BigDecimal getQtdRelatorio(){
        return movimentoMaterial?.entrada_saida == TipoMovimentoMaterial.TIPO_ENTRADA ? quantidade : quantidade * -1
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

    MaterialLote getMaterialLote() {
        // Caso n�o tenha controle de lote e validade, retorna null
        if (!this.material?.controleLoteValidade) return null

        // Busca o lote
        def materialLote = this.material?.materialLote?.find({
            it?.lote == this.lote && it?.lote == this.lote
        })

        // Caso n�o encontre o lote, cria um novo
        if (!materialLote) {
            materialLote = new MaterialLote(material: this.material, fornecedor: null,
                    lote: this.lote, validade_lote: this.validade_lote)
            materialLote.save()

            this.material?.addToMaterialLote(materialLote)
            this.material?.save()
        }

        return materialLote
    }
}