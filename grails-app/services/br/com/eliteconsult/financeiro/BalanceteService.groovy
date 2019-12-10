package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(Balancete)
interface BalanceteService {

    Balancete get(Serializable id)

    List<Balancete> list(Map args)

    Long count()

    void delete(Serializable id)

    Balancete save(Balancete balancete)

}