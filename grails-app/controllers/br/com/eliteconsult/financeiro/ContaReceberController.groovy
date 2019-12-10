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
class ContaReceberController {

    ContaReceberService contaReceberService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond contaReceberService.list(params), model:[contaReceberCount: contaReceberService.count()]
    }

    def show(Long id) {
        respond contaReceberService.get(id)
    }

    @Transactional
    def save(ContaReceber contaReceber) {
        if (contaReceber == null) {
            render status: NOT_FOUND
            return
        }
        if (contaReceber.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaReceber.errors
            return
        }

        try {
            contaReceberService.save(contaReceber)
        } catch (ValidationException e) {
            respond contaReceber.errors
            return
        }

        respond contaReceber, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ContaReceber contaReceber) {
        if (contaReceber == null) {
            render status: NOT_FOUND
            return
        }
        if (contaReceber.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaReceber.errors
            return
        }

        try {
            contaReceberService.save(contaReceber)
        } catch (ValidationException e) {
            respond contaReceber.errors
            return
        }

        respond contaReceber, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        contaReceberService.delete(id)

        render status: NO_CONTENT
    }
}
