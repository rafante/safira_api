package br.com.eliteconsult.cadastros

import grails.gorm.services.Service

@Service(FormaPagamento)
interface FormaPagamentoService {

    FormaPagamento get(Serializable id)

    List<FormaPagamento> list(Map args)

    Long count()

    void delete(Serializable id)

    FormaPagamento save(FormaPagamento formaPagamento)

}