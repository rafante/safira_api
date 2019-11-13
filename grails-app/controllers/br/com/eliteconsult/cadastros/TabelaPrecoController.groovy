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
class TabelaPrecoController {

    TabelaPrecoService tabelaPrecoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tabelaPrecoService.list(params), model:[tabelaPrecoCount: tabelaPrecoService.count()]
    }

    def show(Long id) {
        respond tabelaPrecoService.get(id)
    }

    @Transactional
    def save(TabelaPreco tabelaPreco) {
        if (tabelaPreco == null) {
            render status: NOT_FOUND
            return
        }
        if (tabelaPreco.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tabelaPreco.errors
            return
        }

        try {
            tabelaPrecoService.save(tabelaPreco)
        } catch (ValidationException e) {
            respond tabelaPreco.errors
            return
        }

        respond tabelaPreco, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TabelaPreco tabelaPreco) {
        if (tabelaPreco == null) {
            render status: NOT_FOUND
            return
        }
        if (tabelaPreco.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tabelaPreco.errors
            return
        }

        try {
            tabelaPrecoService.save(tabelaPreco)
        } catch (ValidationException e) {
            respond tabelaPreco.errors
            return
        }

        respond tabelaPreco, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        tabelaPrecoService.delete(id)

        render status: NO_CONTENT
    }
}
