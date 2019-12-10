package br.com.eliteconsult.materiais

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class TabelaConversaoController {

    TabelaConversaoService tabelaConversaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tabelaConversaoService.list(params), model:[tabelaConversaoCount: tabelaConversaoService.count()]
    }

    def show(Long id) {
        respond tabelaConversaoService.get(id)
    }

    @Transactional
    def save(TabelaConversao tabelaConversao) {
        if (tabelaConversao == null) {
            render status: NOT_FOUND
            return
        }
        if (tabelaConversao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tabelaConversao.errors
            return
        }

        try {
            tabelaConversaoService.save(tabelaConversao)
        } catch (ValidationException e) {
            respond tabelaConversao.errors
            return
        }

        respond tabelaConversao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TabelaConversao tabelaConversao) {
        if (tabelaConversao == null) {
            render status: NOT_FOUND
            return
        }
        if (tabelaConversao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tabelaConversao.errors
            return
        }

        try {
            tabelaConversaoService.save(tabelaConversao)
        } catch (ValidationException e) {
            respond tabelaConversao.errors
            return
        }

        respond tabelaConversao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        tabelaConversaoService.delete(id)

        render status: NO_CONTENT
    }
}
