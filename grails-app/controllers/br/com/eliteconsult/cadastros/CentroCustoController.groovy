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
class CentroCustoController {

    CentroCustoService centroCustoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond centroCustoService.list(params), model:[centroCustoCount: centroCustoService.count()]
    }

    def show(Long id) {
        respond centroCustoService.get(id)
    }

    @Transactional
    def save(CentroCusto centroCusto) {
        if (centroCusto == null) {
            render status: NOT_FOUND
            return
        }
        if (centroCusto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond centroCusto.errors
            return
        }

        try {
            centroCustoService.save(centroCusto)
        } catch (ValidationException e) {
            respond centroCusto.errors
            return
        }

        respond centroCusto, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(CentroCusto centroCusto) {
        if (centroCusto == null) {
            render status: NOT_FOUND
            return
        }
        if (centroCusto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond centroCusto.errors
            return
        }

        try {
            centroCustoService.save(centroCusto)
        } catch (ValidationException e) {
            respond centroCusto.errors
            return
        }

        respond centroCusto, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        centroCustoService.delete(id)

        render status: NO_CONTENT
    }
}
