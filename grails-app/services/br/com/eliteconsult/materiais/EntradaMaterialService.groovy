package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(EntradaMaterial)
interface EntradaMaterialService {

    EntradaMaterial get(Serializable id)

    List<EntradaMaterial> list(Map args)

    Long count()

    void delete(Serializable id)

    EntradaMaterial save(EntradaMaterial entradaMaterial)

}