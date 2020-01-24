package br.com.eliteconsult.materiais

class UnidadeMedida {

    String unidade
    String descricao

    String toString() {
        return this.descricao
    }

    String getSPEDReg0190(sep) {
        return sep + "0190" +
                sep + this.unidade +
                sep + this.descricao + sep + '\r\n'
    }
}
