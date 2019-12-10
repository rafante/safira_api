package br.com.eliteconsult.ev

import grails.gorm.services.Service

@Service(Exame)
interface ExameService {

    Exame get(Serializable id)

    List<Exame> list(Map args)

    Long count()

    void delete(Serializable id)

    Exame save(Exame exame)

}