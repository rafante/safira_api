package br.com.eliteconsult.config

import grails.gorm.services.Service

@Service(Periodicidade)
interface PeriodicidadeService {

    Periodicidade get(Serializable id)

    List<Periodicidade> list(Map args)

    Long count()

    void delete(Serializable id)

    Periodicidade save(Periodicidade periodicidade)

}