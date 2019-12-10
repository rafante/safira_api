package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(MovimentoFinanceiro)
interface MovimentoFinanceiroService {

    MovimentoFinanceiro get(Serializable id)

    List<MovimentoFinanceiro> list(Map args)

    Long count()

    void delete(Serializable id)

    MovimentoFinanceiro save(MovimentoFinanceiro movimentoFinanceiro)

}