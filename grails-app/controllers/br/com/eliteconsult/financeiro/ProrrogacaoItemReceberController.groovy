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
class ProrrogacaoItemReceberController {

    ProrrogacaoItemReceberService prorrogacaoItemReceberService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond prorrogacaoItemReceberService.list(params), model:[prorrogacaoItemReceberCount: prorrogacaoItemReceberService.count()]
    }

    def show(Long id) {
        respond prorrogacaoItemReceberService.get(id)
    }

    @Transactional
    def save(ProrrogacaoItemReceber prorrogacaoItemReceber) {
        if (prorrogacaoItemReceber == null) {
            render status: NOT_FOUND
            return
        }
        if (prorrogacaoItemReceber.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prorrogacaoItemReceber.errors
            return
        }

        try {
            prorrogacaoItemReceberService.save(prorrogacaoItemReceber)
        } catch (ValidationException e) {
            respond prorrogacaoItemReceber.errors
            return
        }

        respond prorrogacaoItemReceber, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ProrrogacaoItemReceber prorrogacaoItemReceber) {
        if (prorrogacaoItemReceber == null) {
            render status: NOT_FOUND
            return
        }
        if (prorrogacaoItemReceber.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prorrogacaoItemReceber.errors
            return
        }

        try {
            prorrogacaoItemReceberService.save(prorrogacaoItemReceber)
        } catch (ValidationException e) {
            respond prorrogacaoItemReceber.errors
            return
        }

        respond prorrogacaoItemReceber, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        prorrogacaoItemReceberService.delete(id)

        render status: NO_CONTENT
    }
}
