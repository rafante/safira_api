package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(CompensacaoItemPagar)
interface CompensacaoItemPagarService {

    CompensacaoItemPagar get(Serializable id)

    List<CompensacaoItemPagar> list(Map args)

    Long count()

    void delete(Serializable id)

    CompensacaoItemPagar save(CompensacaoItemPagar compensacaoItemPagar)

}