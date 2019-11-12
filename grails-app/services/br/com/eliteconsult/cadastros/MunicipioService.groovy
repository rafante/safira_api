package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(Municipio)
interface MunicipioService {

    Municipio get(Serializable id)

    List<Municipio> list(Map args)

    Long count()

    void delete(Serializable id)

    Municipio save(Municipio municipio)

}