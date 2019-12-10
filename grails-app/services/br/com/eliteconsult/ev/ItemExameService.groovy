package br.com.eliteconsult.ev

import grails.gorm.services.Service

@Service(ItemExame)
interface ItemExameService {

    ItemExame get(Serializable id)

    List<ItemExame> list(Map args)

    Long count()

    void delete(Serializable id)

    ItemExame save(ItemExame itemExame)

}