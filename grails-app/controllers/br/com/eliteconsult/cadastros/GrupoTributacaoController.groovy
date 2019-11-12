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
class GrupoTributacaoController {

    GrupoTributacaoService grupoTributacaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond grupoTributacaoService.list(params), model:[grupoTributacaoCount: grupoTributacaoService.count()]
    }

    def show(Long id) {
        respond grupoTributacaoService.get(id)
    }

    @Transactional
    def save(GrupoTributacao grupoTributacao) {
        if (grupoTributacao == null) {
            render status: NOT_FOUND
            return
        }
        if (grupoTributacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grupoTributacao.errors
            return
        }

        try {
            grupoTributacaoService.save(grupoTributacao)
        } catch (ValidationException e) {
            respond grupoTributacao.errors
            return
        }

        respond grupoTributacao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(GrupoTributacao grupoTributacao) {
        if (grupoTributacao == null) {
            render status: NOT_FOUND
            return
        }
        if (grupoTributacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grupoTributacao.errors
            return
        }

        try {
            grupoTributacaoService.save(grupoTributacao)
        } catch (ValidationException e) {
            respond grupoTributacao.errors
            return
        }

        respond grupoTributacao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        grupoTributacaoService.delete(id)

        render status: NO_CONTENT
    }
}
