package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(Recipiente)
interface RecipienteService {

    Recipiente get(Serializable id)

    List<Recipiente> list(Map args)

    Long count()

    void delete(Serializable id)

    Recipiente save(Recipiente recipiente)

}