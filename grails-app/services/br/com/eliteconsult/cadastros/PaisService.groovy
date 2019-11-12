package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(Pais)
interface PaisService {

    Pais get(Serializable id)

    List<Pais> list(Map args)

    Long count()

    void delete(Serializable id)

    Pais save(Pais pais)

}