package base

abstract class ProrrogacaoItemFinanceiroBase {
    Date data = new Date()
    String justificativa

    static constraints = {
        data()
        justificativa(size:1..5000)
    }
}
