package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(TabelaPrecoMaterial)
interface TabelaPrecoMaterialService {

    TabelaPrecoMaterial get(Serializable id)

    List<TabelaPrecoMaterial> list(Map args)

    Long count()

    void delete(Serializable id)

    TabelaPrecoMaterial save(TabelaPrecoMaterial tabelaPrecoMaterial)

}