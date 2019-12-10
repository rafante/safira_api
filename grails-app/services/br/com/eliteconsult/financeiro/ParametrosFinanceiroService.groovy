package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(ParametrosFinanceiro)
interface ParametrosFinanceiroService {

    ParametrosFinanceiro get(Serializable id)

    List<ParametrosFinanceiro> list(Map args)

    Long count()

    void delete(Serializable id)

    ParametrosFinanceiro save(ParametrosFinanceiro parametrosFinanceiro)

}