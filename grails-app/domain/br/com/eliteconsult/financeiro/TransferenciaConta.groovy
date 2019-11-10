package br.com.eliteconsult.financeiro

import br.com.eliteconsult.cadastros.HistoricoPadrao

public class TransferenciaConta {
    Date data
    String descricao
    HistoricoPadrao historicoPadrao
    ContaCorrente contaCorrenteDestino
    ContaCorrente contaCorrenteOrigem
    BigDecimal valor
    Boolean estornado

    static hasMany = [
            movimentoFinanceiro: MovimentoFinanceiro
    ]

    static constraints = {
        valor(scale: 2)
        contaCorrenteDestino(nullable: false, validator: {val, obj ->
            return val.id != obj.contaCorrenteOrigem.id
        })
        contaCorrenteOrigem(nullable: false, validator: { val, obj ->
            return val.id != obj.contaCorrenteDestino.id
        })
        movimentoFinanceiro(cascade: 'all-delete-orphan')
    }

    def estornar() {
        for (MovimentoFinanceiro mov in movimentoFinanceiro) {
            mov.estornar()
        }

        this.estornado = true
    }
}