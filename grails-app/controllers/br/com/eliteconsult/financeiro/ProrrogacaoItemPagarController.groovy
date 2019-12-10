package br.com.eliteconsult.financeiro

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ProrrogacaoItemPagarController {

    ProrrogacaoItemPagarService prorrogacaoItemPagarService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond prorrogacaoItemPagarService.list(params), model:[prorrogacaoItemPagarCount: prorrogacaoItemPagarService.count()]
    }

    def show(Long id) {
        respond prorrogacaoItemPagarService.get(id)
    }

    @Transactional
    def save(ProrrogacaoItemPagar prorrogacaoItemPagar) {
        if (prorrogacaoItemPagar == null) {
            render status: NOT_FOUND
            return
        }
        if (prorrogacaoItemPagar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prorrogacaoItemPagar.errors
            return
        }

        try {
            prorrogacaoItemPagarService.save(prorrogacaoItemPagar)
        } catch (ValidationException e) {
            respond prorrogacaoItemPagar.errors
            return
        }

        respond prorrogacaoItemPagar, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ProrrogacaoItemPagar prorrogacaoItemPagar) {
        if (prorrogacaoItemPagar == null) {
            render status: NOT_FOUND
            return
        }
        if (prorrogacaoItemPagar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prorrogacaoItemPagar.errors
            return
        }

        try {
            prorrogacaoItemPagarService.save(prorrogacaoItemPagar)
        } catch (ValidationException e) {
            respond prorrogacaoItemPagar.errors
            return
        }

        respond prorrogacaoItemPagar, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        prorrogacaoItemPagarService.delete(id)

        render status: NO_CONTENT
    }
}
