package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(HistoricoPadrao)
interface HistoricoPadraoService {

    HistoricoPadrao get(Serializable id)

    List<HistoricoPadrao> list(Map args)

    Long count()

    void delete(Serializable id)

    HistoricoPadrao save(HistoricoPadrao historicoPadrao)

}