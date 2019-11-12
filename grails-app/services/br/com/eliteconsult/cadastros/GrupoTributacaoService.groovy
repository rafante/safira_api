package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(GrupoTributacao)
interface GrupoTributacaoService {

    GrupoTributacao get(Serializable id)

    List<GrupoTributacao> list(Map args)

    Long count()

    void delete(Serializable id)

    GrupoTributacao save(GrupoTributacao grupoTributacao)

}