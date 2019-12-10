package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(MaterialLote)
interface MaterialLoteService {

    MaterialLote get(Serializable id)

    List<MaterialLote> list(Map args)

    Long count()

    void delete(Serializable id)

    MaterialLote save(MaterialLote materialLote)

}