package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(TipoConta)
interface TipoContaService {

    TipoConta get(Serializable id)

    List<TipoConta> list(Map args)

    Long count()

    void delete(Serializable id)

    TipoConta save(TipoConta tipoConta)

}