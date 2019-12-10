package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(ContaReceber)
interface ContaReceberService {

    ContaReceber get(Serializable id)

    List<ContaReceber> list(Map args)

    Long count()

    void delete(Serializable id)

    ContaReceber save(ContaReceber contaReceber)

}