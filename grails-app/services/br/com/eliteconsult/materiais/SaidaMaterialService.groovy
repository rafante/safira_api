package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(SaidaMaterial)
interface SaidaMaterialService {

    SaidaMaterial get(Serializable id)

    List<SaidaMaterial> list(Map args)

    Long count()

    void delete(Serializable id)

    SaidaMaterial save(SaidaMaterial saidaMaterial)

}