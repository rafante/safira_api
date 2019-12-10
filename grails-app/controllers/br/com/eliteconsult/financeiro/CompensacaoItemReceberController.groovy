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
class CompensacaoItemReceberController {

    CompensacaoItemReceberService compensacaoItemReceberService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond compensacaoItemReceberService.list(params), model:[compensacaoItemReceberCount: compensacaoItemReceberService.count()]
    }

    def show(Long id) {
        respond compensacaoItemReceberService.get(id)
    }

    @Transactional
    def save(CompensacaoItemReceber compensacaoItemReceber) {
        if (compensacaoItemReceber == null) {
            render status: NOT_FOUND
            return
        }
        if (compensacaoItemReceber.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond compensacaoItemReceber.errors
            return
        }

        try {
            compensacaoItemReceberService.save(compensacaoItemReceber)
        } catch (ValidationException e) {
            respond compensacaoItemReceber.errors
            return
        }

        respond compensacaoItemReceber, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(CompensacaoItemReceber compensacaoItemReceber) {
        if (compensacaoItemReceber == null) {
            render status: NOT_FOUND
            return
        }
        if (compensacaoItemReceber.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond compensacaoItemReceber.errors
            return
        }

        try {
            compensacaoItemReceberService.save(compensacaoItemReceber)
        } catch (ValidationException e) {
            respond compensacaoItemReceber.errors
            return
        }

        respond compensacaoItemReceber, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        compensacaoItemReceberService.delete(id)

        render status: NO_CONTENT
    }
}
