package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(Localizacao)
interface LocalizacaoService {

    Localizacao get(Serializable id)

    List<Localizacao> list(Map args)

    Long count()

    void delete(Serializable id)

    Localizacao save(Localizacao localizacao)

}