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
class SubGrupoParceiroNegociosController {

    SubGrupoParceiroNegociosService subGrupoParceiroNegociosService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond subGrupoParceiroNegociosService.list(params), model:[subGrupoParceiroNegociosCount: subGrupoParceiroNegociosService.count()]
    }

    def show(Long id) {
        respond subGrupoParceiroNegociosService.get(id)
    }

    @Transactional
    def save(SubGrupoParceiroNegocios subGrupoParceiroNegocios) {
        if (subGrupoParceiroNegocios == null) {
            render status: NOT_FOUND
            return
        }
        if (subGrupoParceiroNegocios.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond subGrupoParceiroNegocios.errors
            return
        }

        try {
            subGrupoParceiroNegociosService.save(subGrupoParceiroNegocios)
        } catch (ValidationException e) {
            respond subGrupoParceiroNegocios.errors
            return
        }

        respond subGrupoParceiroNegocios, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SubGrupoParceiroNegocios subGrupoParceiroNegocios) {
        if (subGrupoParceiroNegocios == null) {
            render status: NOT_FOUND
            return
        }
        if (subGrupoParceiroNegocios.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond subGrupoParceiroNegocios.errors
            return
        }

        try {
            subGrupoParceiroNegociosService.save(subGrupoParceiroNegocios)
        } catch (ValidationException e) {
            respond subGrupoParceiroNegocios.errors
            return
        }

        respond subGrupoParceiroNegocios, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        subGrupoParceiroNegociosService.delete(id)

        render status: NO_CONTENT
    }
}
