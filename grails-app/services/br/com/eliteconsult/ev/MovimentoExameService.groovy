package br.com.eliteconsult.ev

import grails.gorm.services.Service

@Service(MovimentoExame)
interface MovimentoExameService {

    MovimentoExame get(Serializable id)

    List<MovimentoExame> list(Map args)

    Long count()

    void delete(Serializable id)

    MovimentoExame save(MovimentoExame movimentoExame)

}