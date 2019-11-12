package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(SubGrupoParceiroNegocios)
interface SubGrupoParceiroNegociosService {

    SubGrupoParceiroNegocios get(Serializable id)

    List<SubGrupoParceiroNegocios> list(Map args)

    Long count()

    void delete(Serializable id)

    SubGrupoParceiroNegocios save(SubGrupoParceiroNegocios subGrupoParceiroNegocios)

}