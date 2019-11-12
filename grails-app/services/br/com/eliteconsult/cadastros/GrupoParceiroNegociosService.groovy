package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(GrupoParceiroNegocios)
interface GrupoParceiroNegociosService {

    GrupoParceiroNegocios get(Serializable id)

    List<GrupoParceiroNegocios> list(Map args)

    Long count()

    void delete(Serializable id)

    GrupoParceiroNegocios save(GrupoParceiroNegocios grupoParceiroNegocios)

}