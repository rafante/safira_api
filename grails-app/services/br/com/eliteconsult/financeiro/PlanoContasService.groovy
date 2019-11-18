package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(PlanoContas)
interface PlanoContasService {

    PlanoContas get(Serializable id)

    List<PlanoContas> list(Map args)

    Long count()

    void delete(Serializable id)

    PlanoContas save(PlanoContas planoContas)

}