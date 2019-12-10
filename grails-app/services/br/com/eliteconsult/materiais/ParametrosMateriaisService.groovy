package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(ParametrosMateriais)
interface ParametrosMateriaisService {

    ParametrosMateriais get(Serializable id)

    List<ParametrosMateriais> list(Map args)

    Long count()

    void delete(Serializable id)

    ParametrosMateriais save(ParametrosMateriais parametrosMateriais)

}