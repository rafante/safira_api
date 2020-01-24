package br.com.eliteconsult.materiais

import br.com.eliteconsult.seguranca.Usuario
import br.com.eliteconsult.cadastros.CentroCusto

class MovimentoMaterial {

    Date data_movimento = new Date()
    String observacao
    StatusMovimento status = StatusMovimento.SOLICITACAO
    Usuario usuario_correcao
    String motivo
    BigDecimal total
    TipoMovimentoMaterial entrada_saida = TipoMovimentoMaterial.TIPO_ENTRADA

    static transients = ["total"]

    static belongsTo = [centroCusto: CentroCusto]

    static defaultFilterFields = "centroCusto;entrada_saida;data_movimento"

    static hasMany = [itemMovimentoMaterial: ItemMovimentoMaterial]

    boolean canEdit() {
        return (status == StatusMovimento.SOLICITACAO)
    }

    void corrigirSaida(usuario, motivo_correcao) {
        status = StatusMovimento.SOLICITACAO
        usuario_correcao = usuario
        motivo = motivo_correcao

        data_movimento = null
        geraMovimentoMaterial(1, "movimentoMaterialItem", "corrigir")
    }

    void geraMovimento(modificador){
        itemMovimentoMaterial.each {
            it.geraMovimento(modificador)
        }
    }

    void geraMovimentoMaterial(int modificador, String controller, String action) {
        itemMovimentoMaterial?.each() {
            it.geraMovimentoMaterial(modificador, controller, action, centroCusto)
        }
    }

    String toString() {
        return id
    }

    BigDecimal getTotal() {
        BigDecimal totalItems = 0

        itemMovimentoMaterial?.each() {
            totalItems = totalItems + it.total
        }

        return totalItems
    }

    def getPossibleStatus() {
        if (this.status == StatusMovimento.SOLICITACAO) return [StatusMovimento.SOLICITACAO, StatusMovimento.CONCLUIDO, StatusMovimento.CANCELADA]
        else if (this.status == StatusMovimento.CONCLUIDO) return [StatusMovimento.CONCLUIDO]
        else if (this.status == StatusMovimento.CANCELADA) return [StatusMovimento.CANCELADA]
    }

}

enum StatusMovimento {
    SOLICITACAO(0),
    CONCLUIDO(1),
    CANCELADA(2)

    final Integer id

    private StatusMovimento(Integer id) {
        this.id = id
    }

    String toString() {
        name()
    }

    Integer value() {
        this.id
    }
}

enum TipoMovimentoMaterial {
    TIPO_ENTRADA("E"),
    TIPO_SAIDA("S")

    final String id

    private TipoMovimentoMaterial(String id) {
        this.id = id
    }

    String toString() {
        switch (this.id) {
            case "E":
                return "Entrada"
            case "S":
                return "Sa�da"
        }
    }

    String value() {
        this.id
    }
}
