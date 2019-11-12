package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(Estado)
interface EstadoService {

    Estado get(Serializable id)

    List<Estado> list(Map args)

    Long count()

    void delete(Serializable id)

    Estado save(Estado estado)

}