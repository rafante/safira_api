package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(ItemMovimentoMaterial)
interface ItemMovimentoMaterialService {

    ItemMovimentoMaterial get(Serializable id)

    List<ItemMovimentoMaterial> list(Map args)

    Long count()

    void delete(Serializable id)

    ItemMovimentoMaterial save(ItemMovimentoMaterial itemMovimentoMaterial)

}