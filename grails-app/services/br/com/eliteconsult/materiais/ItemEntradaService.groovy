package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(ItemEntrada)
interface ItemEntradaService {

    ItemEntrada get(Serializable id)

    List<ItemEntrada> list(Map args)

    Long count()

    void delete(Serializable id)

    ItemEntrada save(ItemEntrada itemEntrada)

}