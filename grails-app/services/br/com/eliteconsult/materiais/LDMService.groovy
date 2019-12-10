package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(LDM)
interface LDMService {

    LDM get(Serializable id)

    List<LDM> list(Map args)

    Long count()

    void delete(Serializable id)

    LDM save(LDM LDM)

}