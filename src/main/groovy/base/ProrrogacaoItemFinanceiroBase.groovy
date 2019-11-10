package base

import com.br.asgardtecnologia.base.Persistente

abstract class ProrrogacaoItemFinanceiroBase extends Persistente {
    Date data = new Date()
    String justificativa

    static constraints = {
        data()
        justificativa(size:1..5000)
    }
}
