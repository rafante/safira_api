package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(ParceiroNegocios)
interface ParceiroNegociosService {

    ParceiroNegocios get(Serializable id)

    List<ParceiroNegocios> list(Map args)

    Long count()

    void delete(Serializable id)

    ParceiroNegocios save(ParceiroNegocios parceiroNegocios)

}