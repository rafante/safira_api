package br.com.eliteconsult.materiais

import grails.gorm.services.Service

@Service(DescricaoFornecedor)
interface DescricaoFornecedorService {

    DescricaoFornecedor get(Serializable id)

    List<DescricaoFornecedor> list(Map args)

    Long count()

    void delete(Serializable id)

    DescricaoFornecedor save(DescricaoFornecedor descricaoFornecedor)

}