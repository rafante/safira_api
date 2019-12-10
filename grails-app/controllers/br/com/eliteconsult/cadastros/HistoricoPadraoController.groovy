package br.com.eliteconsult.cadastros

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class HistoricoPadraoController {

    HistoricoPadraoService historicoPadraoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond historicoPadraoService.list(params), model:[historicoPadraoCount: historicoPadraoService.count()]
    }

    def show(Long id) {
        respond historicoPadraoService.get(id)
    }

    @Transactional
    def save(HistoricoPadrao historicoPadrao) {
        if (historicoPadrao == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoPadrao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoPadrao.errors
            return
        }

        try {
            historicoPadraoService.save(historicoPadrao)
        } catch (ValidationException e) {
            respond historicoPadrao.errors
            return
        }

        respond historicoPadrao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(HistoricoPadrao historicoPadrao) {
        if (historicoPadrao == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoPadrao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoPadrao.errors
            return
        }

        try {
            historicoPadraoService.save(historicoPadrao)
        } catch (ValidationException e) {
            respond historicoPadrao.errors
            return
        }

        respond historicoPadrao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        historicoPadraoService.delete(id)

        render status: NO_CONTENT
    }
}
