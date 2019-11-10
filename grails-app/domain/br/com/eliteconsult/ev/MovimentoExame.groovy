package br.com.eliteconsult.ev

class MovimentoExame {

    Date dataMovimento
    BigDecimal quantidade

    static belongsTo = [exame: Exame]

    static constraints = {

    }
}
