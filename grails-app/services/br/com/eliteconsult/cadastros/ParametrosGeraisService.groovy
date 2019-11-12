package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(ParametrosGerais)
interface ParametrosGeraisService {

    ParametrosGerais get(Serializable id)

    List<ParametrosGerais> list(Map args)

    Long count()

    void delete(Serializable id)

    ParametrosGerais save(ParametrosGerais parametrosGerais)

}