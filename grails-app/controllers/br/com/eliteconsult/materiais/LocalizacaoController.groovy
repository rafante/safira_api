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
class LocalizacaoController {

    LocalizacaoService localizacaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond localizacaoService.list(params), model:[localizacaoCount: localizacaoService.count()]
    }

    def show(Long id) {
        respond localizacaoService.get(id)
    }

    @Transactional
    def save(Localizacao localizacao) {
        if (localizacao == null) {
            render status: NOT_FOUND
            return
        }
        if (localizacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond localizacao.errors
            return
        }

        try {
            localizacaoService.save(localizacao)
        } catch (ValidationException e) {
            respond localizacao.errors
            return
        }

        respond localizacao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Localizacao localizacao) {
        if (localizacao == null) {
            render status: NOT_FOUND
            return
        }
        if (localizacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond localizacao.errors
            return
        }

        try {
            localizacaoService.save(localizacao)
        } catch (ValidationException e) {
            respond localizacao.errors
            return
        }

        respond localizacao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        localizacaoService.delete(id)

        render status: NO_CONTENT
    }
}
