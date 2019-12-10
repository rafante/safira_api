package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(Banco)
interface BancoService {

    Banco get(Serializable id)

    List<Banco> list(Map args)

    Long count()

    void delete(Serializable id)

    Banco save(Banco banco)

}