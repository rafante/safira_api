package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(Empresa)
interface EmpresaService {

    Empresa get(Serializable id)

    List<Empresa> list(Map args)

    Long count()

    void delete(Serializable id)

    Empresa save(Empresa empresa)

}