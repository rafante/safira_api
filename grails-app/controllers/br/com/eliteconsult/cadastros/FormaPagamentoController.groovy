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
class FormaPagamentoController {

    FormaPagamentoService formaPagamentoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond formaPagamentoService.list(params), model:[formaPagamentoCount: formaPagamentoService.count()]
    }

    def show(Long id) {
        respond formaPagamentoService.get(id)
    }

    @Transactional
    def save(FormaPagamento formaPagamento) {
        if (formaPagamento == null) {
            render status: NOT_FOUND
            return
        }
        if (formaPagamento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond formaPagamento.errors
            return
        }

        try {
            formaPagamentoService.save(formaPagamento)
        } catch (ValidationException e) {
            respond formaPagamento.errors
            return
        }

        respond formaPagamento, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(FormaPagamento formaPagamento) {
        if (formaPagamento == null) {
            render status: NOT_FOUND
            return
        }
        if (formaPagamento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond formaPagamento.errors
            return
        }

        try {
            formaPagamentoService.save(formaPagamento)
        } catch (ValidationException e) {
            respond formaPagamento.errors
            return
        }

        respond formaPagamento, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        formaPagamentoService.delete(id)

        render status: NO_CONTENT
    }
}
