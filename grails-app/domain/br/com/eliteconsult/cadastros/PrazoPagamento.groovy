package br.com.eliteconsult.cadastros

public class PrazoPagamento {

    String descricao

    static hasMany = [
            itemPrazoPagamento: ItemPrazoPagamento
    ]

    static defaultValueField = "descricao"
    static defaultFilterFields = "descricao"

    static constraints = {
        /**
         * Tamanho 60
         */
        descricao(size: 0..60)
        itemPrazoPagamento()
    }

    String toString() {
        return descricao
    }

    def geraParcelas(BigDecimal valorTotal, Date vencimentoBase) {
        def itens = []

        // Resto das parcelas
        def resto = valorTotal

        for (ItemPrazoPagamento itemPrazo in itemPrazoPagamento) {
            def item = [:]

            use(groovy.time.TimeCategory) {
                item.data = vencimentoBase + (itemPrazo.prazo).days
                item.valor = (valorTotal * itemPrazo.percentual) / 100
                itens.add(item)
            }

            resto -= item.valor
        }

        // Primeira parcela (inclui o resto)
        if (resto) itens[0].valor = itens[0]?.valor + resto

        return itens
    }


}