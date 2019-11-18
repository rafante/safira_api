package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(UnidadeMedida)
interface UnidadeMedidaService {

    UnidadeMedida get(Serializable id)

    List<UnidadeMedida> list(Map args)

    Long count()

    void delete(Serializable id)

    UnidadeMedida save(UnidadeMedida unidadeMedida)

}