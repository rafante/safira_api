package br.com.eliteconsult.materiais

import br.com.eliteconsult.seguranca.Usuario
import br.com.eliteconsult.cadastros.CentroCusto

public class SaidaMaterial {

    Date data_saida = new Date()
    String observacao
    StatusSaida status = StatusSaida.PENDENTE
    Usuario usuario_correcao
    String motivo
    BigDecimal total
    TipoMovimentoMaterial entrada_saida

    static transients = ["total"]

    static belongsTo = [centroCusto: CentroCusto]

    static defaultFilterFields = "centroCusto;entrada_saida"

    static hasMany = [itemSaida: ItemSaida]

    boolean canEdit() {
        return (status == StatusSaida.PENDENTE)
    }

    void corrigirSaida(usuario, motivo_correcao) {
        status = StatusSaida.PENDENTE
        usuario_correcao = usuario
        motivo = motivo_correcao

        data_saida = null
        geraMovimentoMaterial(1, "saidaItem", "corrigir")
    }

    void geraMovimentoMaterial(int modificador, String controller, String action) {
        itemSaida?.each() {
            it.geraMovimentoMaterial(modificador, controller, action, centroCusto)
        }
    }

    String toString() {
        return id
    }

    BigDecimal getTotal() {
        BigDecimal totalItems = 0

        itemSaida?.each() {
            totalItems = totalItems + it.total
        }

        return totalItems
    }

    def getPossibleStatus() {
        if (this.status == StatusSaida.PENDENTE) return [StatusSaida.PENDENTE, StatusSaida.SAIDA, StatusSaida.CANCELADA]
        else if (this.status == StatusSaida.SAIDA) return [StatusSaida.SAIDA]
        else if (this.status == StatusSaida.CANCELADA) return [StatusSaida.CANCELADA]
    }

}

enum StatusSaida {
    PENDENTE(0),
    SAIDA(1),
    CANCELADA(2)

    final Integer id

    private StatusSaida(Integer id) {
        this.id = id
    }

    String toString() {
        name()
    }

    Integer value() {
        this.id
    }
}
