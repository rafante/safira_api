package br.com.eliteconsult.financeiro

import grails.gorm.services.Service

@Service(Cheque)
interface ChequeService {

    Cheque get(Serializable id)

    List<Cheque> list(Map args)

    Long count()

    void delete(Serializable id)

    Cheque save(Cheque cheque)

}