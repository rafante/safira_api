package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(NCM)
interface NCMService {

    NCM get(Serializable id)

    List<NCM> list(Map args)

    Long count()

    void delete(Serializable id)

    NCM save(NCM NCM)

}