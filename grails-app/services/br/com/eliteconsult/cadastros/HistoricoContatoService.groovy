package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(HistoricoContato)
interface HistoricoContatoService {

    HistoricoContato get(Serializable id)

    List<HistoricoContato> list(Map args)

    Long count()

    void delete(Serializable id)

    HistoricoContato save(HistoricoContato historicoContato)

}