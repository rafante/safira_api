package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(Endereco)
interface EnderecoService {

    Endereco get(Serializable id)

    List<Endereco> list(Map args)

    Long count()

    void delete(Serializable id)

    Endereco save(Endereco endereco)

}