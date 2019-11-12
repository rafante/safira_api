package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(PrazoPagamento)
interface PrazoPagamentoService {

    PrazoPagamento get(Serializable id)

    List<PrazoPagamento> list(Map args)

    Long count()

    void delete(Serializable id)

    PrazoPagamento save(PrazoPagamento prazoPagamento)

}