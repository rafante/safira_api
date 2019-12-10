package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(TabelaConversao)
interface TabelaConversaoService {

    TabelaConversao get(Serializable id)

    List<TabelaConversao> list(Map args)

    Long count()

    void delete(Serializable id)

    TabelaConversao save(TabelaConversao tabelaConversao)

}