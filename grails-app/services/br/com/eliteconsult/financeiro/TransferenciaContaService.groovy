package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(TransferenciaConta)
interface TransferenciaContaService {

    TransferenciaConta get(Serializable id)

    List<TransferenciaConta> list(Map args)

    Long count()

    void delete(Serializable id)

    TransferenciaConta save(TransferenciaConta transferenciaConta)

}