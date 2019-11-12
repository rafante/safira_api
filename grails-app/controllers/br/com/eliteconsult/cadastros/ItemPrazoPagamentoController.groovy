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
class ItemPrazoPagamentoController {

    ItemPrazoPagamentoService itemPrazoPagamentoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond itemPrazoPagamentoService.list(params), model:[itemPrazoPagamentoCount: itemPrazoPagamentoService.count()]
    }

    def show(Long id) {
        respond itemPrazoPagamentoService.get(id)
    }

    @Transactional
    def save(ItemPrazoPagamento itemPrazoPagamento) {
        if (itemPrazoPagamento == null) {
            render status: NOT_FOUND
            return
        }
        if (itemPrazoPagamento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemPrazoPagamento.errors
            return
        }

        try {
            itemPrazoPagamentoService.save(itemPrazoPagamento)
        } catch (ValidationException e) {
            respond itemPrazoPagamento.errors
            return
        }

        respond itemPrazoPagamento, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ItemPrazoPagamento itemPrazoPagamento) {
        if (itemPrazoPagamento == null) {
            render status: NOT_FOUND
            return
        }
        if (itemPrazoPagamento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond itemPrazoPagamento.errors
            return
        }

        try {
            itemPrazoPagamentoService.save(itemPrazoPagamento)
        } catch (ValidationException e) {
            respond itemPrazoPagamento.errors
            return
        }

        respond itemPrazoPagamento, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        itemPrazoPagamentoService.delete(id)

        render status: NO_CONTENT
    }
}
