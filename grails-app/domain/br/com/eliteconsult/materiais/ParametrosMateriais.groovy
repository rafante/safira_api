package br.com.eliteconsult.materiais

public class ParametrosMateriais {

    TipoCalculoCustoEntrada tipoCalculoCustoEntradaDefault = TipoCalculoCustoEntrada.ULTIMO_CUSTO
    Integer mesesCalculoEstoquePA = 6 // PA = Produto Acabadp
    Integer mesesCalculoEstoquePP = 6 // PP = Para Produção

    static constraints = {
        tipoCalculoCustoEntradaDefault()
        mesesCalculoEstoquePA()
        mesesCalculoEstoquePP()
    }

}

enum TipoCalculoCustoEntrada {
    ULTIMO_CUSTO(0),
    CUSTO_MEDIO(1)

    final Integer id

    private TipoCalculoCustoEntrada(Integer id) {
        this.id = id
    }

    String toString() {
        name()
    }

    Integer value() {
        this.id
    }
}
