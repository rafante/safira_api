package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(CentroCusto)
interface CentroCustoService {

    CentroCusto get(Serializable id)

    List<CentroCusto> list(Map args)

    Long count()

    void delete(Serializable id)

    CentroCusto save(CentroCusto centroCusto)

}