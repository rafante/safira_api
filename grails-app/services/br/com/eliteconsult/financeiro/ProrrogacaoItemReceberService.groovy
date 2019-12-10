package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(ProrrogacaoItemReceber)
interface ProrrogacaoItemReceberService {

    ProrrogacaoItemReceber get(Serializable id)

    List<ProrrogacaoItemReceber> list(Map args)

    Long count()

    void delete(Serializable id)

    ProrrogacaoItemReceber save(ProrrogacaoItemReceber prorrogacaoItemReceber)

}