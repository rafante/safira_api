package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(ContaPagar)
interface ContaPagarService {

    ContaPagar get(Serializable id)

    List<ContaPagar> list(Map args)

    Long count()

    void delete(Serializable id)

    ContaPagar save(ContaPagar contaPagar)

}