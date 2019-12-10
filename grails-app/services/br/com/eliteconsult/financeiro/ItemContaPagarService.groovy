package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(ItemContaPagar)
interface ItemContaPagarService {

    ItemContaPagar get(Serializable id)

    List<ItemContaPagar> list(Map args)

    Long count()

    void delete(Serializable id)

    ItemContaPagar save(ItemContaPagar itemContaPagar)

}