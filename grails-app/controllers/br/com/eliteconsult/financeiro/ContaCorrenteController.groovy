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
class ContaCorrenteController {

    ContaCorrenteService contaCorrenteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond contaCorrenteService.list(params), model:[contaCorrenteCount: contaCorrenteService.count()]
    }

    def show(Long id) {
        respond contaCorrenteService.get(id)
    }

    @Transactional
    def save(ContaCorrente contaCorrente) {
        if (contaCorrente == null) {
            render status: NOT_FOUND
            return
        }
        if (contaCorrente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaCorrente.errors
            return
        }

        try {
            contaCorrenteService.save(contaCorrente)
        } catch (ValidationException e) {
            respond contaCorrente.errors
            return
        }

        respond contaCorrente, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ContaCorrente contaCorrente) {
        if (contaCorrente == null) {
            render status: NOT_FOUND
            return
        }
        if (contaCorrente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contaCorrente.errors
            return
        }

        try {
            contaCorrenteService.save(contaCorrente)
        } catch (ValidationException e) {
            respond contaCorrente.errors
            return
        }

        respond contaCorrente, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        contaCorrenteService.delete(id)

        render status: NO_CONTENT
    }
}
