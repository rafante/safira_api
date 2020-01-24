package br.com.eliteconsult.config

class Periodicidade {

    int ordem
    String descricao
    Boolean diario
    int dias
    int meses
    int anos

    String toString() {
        return descricao
    }

    public Date[] getDatas(int numPeriodos) {
        return getDatas(numPeriodos, new Date(), 0)
    }

    public Date[] getDatas(int numPeriodos, Date primeiraData) {
        return getDatas(numPeriodos, primeiraData, 0)
    }

    public Date[] getDatas(int numPeriodos, Date primeiraData, int diasAdicionais) {
        def datas = []

        datas.add(primeiraData)

        if (numPeriodos == 1) return datas

        if (!diario) diasAdicionais = 0

        // Restante das datas
        for (i in 1..numPeriodos - 1) {
            use(groovy.time.TimeCategory) {
                Date date = primeiraData +
                        ((diasAdicionais + this.dias) * i).days +
                        (this.meses * i).months +
                        (this.anos * i).years

                datas.add(date)
            }
        }

        return datas
    }
}