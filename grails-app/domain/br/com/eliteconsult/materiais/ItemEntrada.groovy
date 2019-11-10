package br.com.eliteconsult.materiais

public class ItemEntrada {

    Integer item
    BigDecimal quantidade
    BigDecimal valor
    BigDecimal icms
    BigDecimal ipi
    BigDecimal pis
    BigDecimal cofins
    BigDecimal descontos
    String lote
    Date validade_lote

    static transients = ["totalLiquido", "totalImpostos", "totalBruto", "materialLote", "lotesPossiveisUI", "validadeLoteUI"]

    static belongsTo = [
            entradaMaterial: EntradaMaterial,
            material: Material,
            unidade_medida: UnidadeMedida]

    static hasMany = [movimento_material: MovimentoMaterial]

    static constraints = {
        item()
        material(cascade: 'save-update')
        quantidade(scale: 5)

        /**
         * Tamanho 5
         */
        unidade_medida()
        valor(scale: 5)
        icms(scale: 5)
        ipi(scale: 5)
        pis(scale: 5)
        cofins(scale: 5)
        descontos(scale: 5)

        movimento_material(cascade: 'all-delete-orphan')
    }

    def beforeInsert = {
        def maxitem = ItemEntrada.withCriteria {
            projections { max("item") }
            eq("entradaMaterial", entradaMaterial)
        }[0]

        if (maxitem) this.item = maxitem + 1
        else this.item = 1
    }

    Boolean geraMovimentoMaterial(int modificador, Boolean atualizaCusto, String controller, String action, centroCusto) {
        def movimento = material.geraMovimentoMaterial(this.materialLote, (quantidade * modificador), valor, this.unidade_medida, controller, action, id, centroCusto)
        if (!movimento) return false

        movimento_material.add(movimento)

        return true
    }

    BigDecimal getTotalLiquido() {
        return descontos ? ((Utils.ToDouble(valor) * Utils.ToDouble(quantidade))) - (Utils.ToDouble(valor) * Utils.ToDouble(descontos / 100))
                         : ((Utils.ToDouble(valor) * Utils.ToDouble(quantidade)))
    }

    BigDecimal getTotalImpostos() {
        return (Utils.ToDouble(icms) + Utils.ToDouble(ipi) + Utils.ToDouble(pis) + Utils.ToDouble(cofins))
    }

    BigDecimal getTotalBruto() {
        return getTotalLiquido() + getTotalImpostos()
    }

    List<MaterialLote> getLotesPossiveisUI(){
        return material ? material.lotesPossiveisUI : null
    }

    Date getValidadeLoteUI(){
//        return new Date()
        return lote ? MaterialLote.findByLote(lote).validade_lote : null;
    }

    String toString() {
        return (item + " - " +
                material?.descricao + " - " +
                quantidade + " - " +
                valor).replace("null - ", "").replace("null", "")
    }

    MaterialLote getMaterialLote() {
        // Caso não tenha controle de lote e validade, retorna null
        if (!this.material?.controleLoteValidade) return null

        // Busca o lote
        def materialLote = this.material?.materialLote?.find({
            it?.fornecedor == this.entradaMaterial.fornecedor && it?.lote == this.lote && it?.lote == this.lote
        })

        // Caso não encontre o lote, cria um novo
        if (!materialLote) {
            materialLote = new MaterialLote(material: this.material, fornecedor: this.entradaMaterial.fornecedor,
                    lote: this.lote, validade_lote: this.validade_lote)
            materialLote.save()

            this.material?.addToMaterialLote(materialLote)
            this.material?.save()
        }

        return materialLote
    }
}