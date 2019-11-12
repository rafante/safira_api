package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(ItemPrazoPagamento)
interface ItemPrazoPagamentoService {

    ItemPrazoPagamento get(Serializable id)

    List<ItemPrazoPagamento> list(Map args)

    Long count()

    void delete(Serializable id)

    ItemPrazoPagamento save(ItemPrazoPagamento itemPrazoPagamento)

}