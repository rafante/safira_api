package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(CompensacaoItemReceber)
interface CompensacaoItemReceberService {

    CompensacaoItemReceber get(Serializable id)

    List<CompensacaoItemReceber> list(Map args)

    Long count()

    void delete(Serializable id)

    CompensacaoItemReceber save(CompensacaoItemReceber compensacaoItemReceber)

}