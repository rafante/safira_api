package br.com.eliteconsult.financeiro

class PlanoContas {

    String codigo
    String descricao
    Natureza natureza

    static defaultValueField = "descricao"
    static defaultFilterFields = "codigo;descricao;natureza"

    static belongsTo = [planoContas: PlanoContas]

    static hasMany = [filhos: PlanoContas]
    static transients = ['temFilhos']

    static constraints = {
        codigo()
        descricao(size: 0..60)
        natureza()
    }

    String toString() {
        return codigo + " - " + descricao
    }

    Boolean getTemFilhos(){
        return filhos?.size()
    }

    def allSubChildren() {
        def children = []
        def removeDuplicates = []
        filhos.each {
            if (!children.contains(it))
                children << it
        }
        def subChildren = []
        filhos.each {
            it.allSubChildren().each{
                subChildren << it
            }
        }
        children.each {
            if (!removeDuplicates.contains(it))
                removeDuplicates << it
        }
        subChildren.each {
            if (!removeDuplicates.contains(it))
                removeDuplicates << it
        }
        return removeDuplicates
    }

    public boolean isChildOf(PlanoContas planoPai) {
        def child = false
        if (planoPai.filhos.contains(this))
            return true

        planoPai.filhos.each { filho ->
            child |= filho.isChildOf(planoPai)
        }
        return child
    }
}

enum Natureza {

    RECEITA(0),
    DESPESA(1)

    final Integer id

    private Natureza(Integer id) {
        this.id = id
    }

    String toString() {
        name()
    }

    Integer value() {
        this.id
    }

    String getSPEDReg0500(sep) {
        return ""
        //        def cod_cta_clean = this.cod_cta.replaceAll('[^a-zA-Z0-9]+','').trim()
        //        
        //        return sep + "0500" +
        //        sep + this.dt_alt?.format("ddMMyyyy") +
        //        sep + this.cod_nat_cc +
        //        sep + this.ind_cta +
        //        sep + this.nivel +
        //        sep + (cod_cta_clean.length() <= 60 ? cod_cta_clean : cod_cta_clean.substring(0,60)) +
        //        sep + this.nome_cta +
        //        sep + this.cod_cta_ref +
        //        sep + this.cnpj_est + sep + '\r\n'
    }

}