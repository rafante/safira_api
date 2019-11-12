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
class PrazoPagamentoController {

    PrazoPagamentoService prazoPagamentoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond prazoPagamentoService.list(params), model:[prazoPagamentoCount: prazoPagamentoService.count()]
    }

    def show(Long id) {
        respond prazoPagamentoService.get(id)
    }

    @Transactional
    def save(PrazoPagamento prazoPagamento) {
        if (prazoPagamento == null) {
            render status: NOT_FOUND
            return
        }
        if (prazoPagamento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prazoPagamento.errors
            return
        }

        try {
            prazoPagamentoService.save(prazoPagamento)
        } catch (ValidationException e) {
            respond prazoPagamento.errors
            return
        }

        respond prazoPagamento, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PrazoPagamento prazoPagamento) {
        if (prazoPagamento == null) {
            render status: NOT_FOUND
            return
        }
        if (prazoPagamento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond prazoPagamento.errors
            return
        }

        try {
            prazoPagamentoService.save(prazoPagamento)
        } catch (ValidationException e) {
            respond prazoPagamento.errors
            return
        }

        respond prazoPagamento, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        prazoPagamentoService.delete(id)

        render status: NO_CONTENT
    }
}
