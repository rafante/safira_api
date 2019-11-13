package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(TipoDocumento)
interface TipoDocumentoService {

    TipoDocumento get(Serializable id)

    List<TipoDocumento> list(Map args)

    Long count()

    void delete(Serializable id)

    TipoDocumento save(TipoDocumento tipoDocumento)

}