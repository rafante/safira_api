package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(HistoricoCustoMaterial)
interface HistoricoCustoMaterialService {

    HistoricoCustoMaterial get(Serializable id)

    List<HistoricoCustoMaterial> list(Map args)

    Long count()

    void delete(Serializable id)

    HistoricoCustoMaterial save(HistoricoCustoMaterial historicoCustoMaterial)

}