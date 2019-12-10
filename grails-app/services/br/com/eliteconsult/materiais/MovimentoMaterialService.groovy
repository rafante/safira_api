package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(MovimentoMaterial)
interface MovimentoMaterialService {

    MovimentoMaterial get(Serializable id)

    List<MovimentoMaterial> list(Map args)

    Long count()

    void delete(Serializable id)

    MovimentoMaterial save(MovimentoMaterial movimentoMaterial)

}