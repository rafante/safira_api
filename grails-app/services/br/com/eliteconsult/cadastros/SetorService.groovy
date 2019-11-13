package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(Setor)
interface SetorService {

    Setor get(Serializable id)

    List<Setor> list(Map args)

    Long count()

    void delete(Serializable id)

    Setor save(Setor setor)

}