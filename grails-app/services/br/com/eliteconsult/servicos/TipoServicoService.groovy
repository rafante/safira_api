package br.com.eliteconsult.servicos

import grails.gorm.services.Service

@Service(TipoServico)
interface TipoServicoService {

    TipoServico get(Serializable id)

    List<TipoServico> list(Map args)

    Long count()

    void delete(Serializable id)

    TipoServico save(TipoServico tipoServico)

}