package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(Grupo)
interface GrupoService {

    Grupo get(Serializable id)

    List<Grupo> list(Map args)

    Long count()

    void delete(Serializable id)

    Grupo save(Grupo grupo)

}