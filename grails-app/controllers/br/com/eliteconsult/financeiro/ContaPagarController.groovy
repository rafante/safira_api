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
class ContaPagarController {

    ContaPagarService contaPagarService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond contaPagarService.list(params), model:[contaPagarCount: contaPagarService.count()]
    }

    def show(Long id) {
        respond contaPagarService.get(id)
    }

    @Transactional
    def save(ContaPagar contaPagar) {
        if (contaPagar == null) {
            render status: NOT_FOUND
            return
        }
        if (contaPagar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaPagar.errors
            return
        }

        try {
            contaPagarService.save(contaPagar)
        } catch (ValidationException e) {
            respond contaPagar.errors
            return
        }

        respond contaPagar, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ContaPagar contaPagar) {
        if (contaPagar == null) {
            render status: NOT_FOUND
            return
        }
        if (contaPagar.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaPagar.errors
            return
        }

        try {
            contaPagarService.save(contaPagar)
        } catch (ValidationException e) {
            respond contaPagar.errors
            return
        }

        respond contaPagar, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        contaPagarService.delete(id)

        render status: NO_CONTENT
    }
}
