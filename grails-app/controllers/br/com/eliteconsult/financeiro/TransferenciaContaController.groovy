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
class TransferenciaContaController {

    TransferenciaContaService transferenciaContaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond transferenciaContaService.list(params), model:[transferenciaContaCount: transferenciaContaService.count()]
    }

    def show(Long id) {
        respond transferenciaContaService.get(id)
    }

    @Transactional
    def save(TransferenciaConta transferenciaConta) {
        if (transferenciaConta == null) {
            render status: NOT_FOUND
            return
        }
        if (transferenciaConta.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transferenciaConta.errors
            return
        }

        try {
            transferenciaContaService.save(transferenciaConta)
        } catch (ValidationException e) {
            respond transferenciaConta.errors
            return
        }

        respond transferenciaConta, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TransferenciaConta transferenciaConta) {
        if (transferenciaConta == null) {
            render status: NOT_FOUND
            return
        }
        if (transferenciaConta.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transferenciaConta.errors
            return
        }

        try {
            transferenciaContaService.save(transferenciaConta)
        } catch (ValidationException e) {
            respond transferenciaConta.errors
            return
        }

        respond transferenciaConta, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        transferenciaContaService.delete(id)

        render status: NO_CONTENT
    }
}
