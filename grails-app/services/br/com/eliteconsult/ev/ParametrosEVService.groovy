package br.com.eliteconsult.ev

import grails.gorm.services.Service

@Service(ParametrosEV)
interface ParametrosEVService {

    ParametrosEV get(Serializable id)

    List<ParametrosEV> list(Map args)

    Long count()

    void delete(Serializable id)

    ParametrosEV save(ParametrosEV parametrosEV)

}