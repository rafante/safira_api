package br.com.eliteconsult.materiais

class UnidadeMedida {

    String unidade
    String descricao

    static constraints = {
        unidade(size: 0..5, blank: false, nullable: false)
        descricao(size: 0..60, blank: false, nullable: false)
    }

    String toString() {
        return this.descricao
    }

    String getSPEDReg0190(sep) {
        return sep + "0190" +
                sep + this.unidade +
                sep + this.descricao + sep + '\r\n'
    }
}
