package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(ItemContaReceber)
interface ItemContaReceberService {

    ItemContaReceber get(Serializable id)

    List<ItemContaReceber> list(Map args)

    Long count()

    void delete(Serializable id)

    ItemContaReceber save(ItemContaReceber itemContaReceber)

}