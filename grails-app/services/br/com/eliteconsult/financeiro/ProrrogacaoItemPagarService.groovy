package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(ProrrogacaoItemPagar)
interface ProrrogacaoItemPagarService {

    ProrrogacaoItemPagar get(Serializable id)

    List<ProrrogacaoItemPagar> list(Map args)

    Long count()

    void delete(Serializable id)

    ProrrogacaoItemPagar save(ProrrogacaoItemPagar prorrogacaoItemPagar)

}