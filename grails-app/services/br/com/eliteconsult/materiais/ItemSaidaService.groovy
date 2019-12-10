package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(ItemSaida)
interface ItemSaidaService {

    ItemSaida get(Serializable id)

    List<ItemSaida> list(Map args)

    Long count()

    void delete(Serializable id)

    ItemSaida save(ItemSaida itemSaida)

}