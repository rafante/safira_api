package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(SubGrupo)
interface SubGrupoService {

    SubGrupo get(Serializable id)

    List<SubGrupo> list(Map args)

    Long count()

    void delete(Serializable id)

    SubGrupo save(SubGrupo subGrupo)

}