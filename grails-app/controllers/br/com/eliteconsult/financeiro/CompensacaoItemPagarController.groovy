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
class CompensacaoItemPagarController {

    CompensacaoItemPagarService compensacaoItemPagarService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond compensacaoItemPagarService.list(params), model:[compensacaoItemPagarCount: compensacaoItemPagarService.count()]
    }

    def show(Long id) {
        respond compensacaoItemPagarService.get(id)
    }

    @Transactional
    def save(CompensacaoItemPagar compensacaoItemPagar) {
        if (compensacaoItemPagar == null) {
            render status: NOT_FOUND
            return
        }
        if (compensacaoItemPagar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond compensacaoItemPagar.errors
            return
        }

        try {
            compensacaoItemPagarService.save(compensacaoItemPagar)
        } catch (ValidationException e) {
            respond compensacaoItemPagar.errors
            return
        }

        respond compensacaoItemPagar, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(CompensacaoItemPagar compensacaoItemPagar) {
        if (compensacaoItemPagar == null) {
            render status: NOT_FOUND
            return
        }
        if (compensacaoItemPagar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond compensacaoItemPagar.errors
            return
        }

        try {
            compensacaoItemPagarService.save(compensacaoItemPagar)
        } catch (ValidationException e) {
            respond compensacaoItemPagar.errors
            return
        }

        respond compensacaoItemPagar, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        compensacaoItemPagarService.delete(id)

        render status: NO_CONTENT
    }
}
