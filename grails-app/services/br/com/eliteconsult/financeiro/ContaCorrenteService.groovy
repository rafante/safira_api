package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(ContaCorrente)
interface ContaCorrenteService {

    ContaCorrente get(Serializable id)

    List<ContaCorrente> list(Map args)

    Long count()

    void delete(Serializable id)

    ContaCorrente save(ContaCorrente contaCorrente)

}