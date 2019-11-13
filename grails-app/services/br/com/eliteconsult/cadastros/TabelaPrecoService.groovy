package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(TabelaPreco)
interface TabelaPrecoService {

    TabelaPreco get(Serializable id)

    List<TabelaPreco> list(Map args)

    Long count()

    void delete(Serializable id)

    TabelaPreco save(TabelaPreco tabelaPreco)

}