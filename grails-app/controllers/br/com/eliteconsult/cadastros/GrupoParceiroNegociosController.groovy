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
class GrupoParceiroNegociosController {

    GrupoParceiroNegociosService grupoParceiroNegociosService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond grupoParceiroNegociosService.list(params), model:[grupoParceiroNegociosCount: grupoParceiroNegociosService.count()]
    }

    def show(Long id) {
        respond grupoParceiroNegociosService.get(id)
    }

    @Transactional
    def save(GrupoParceiroNegocios grupoParceiroNegocios) {
        if (grupoParceiroNegocios == null) {
            render status: NOT_FOUND
            return
        }
        if (grupoParceiroNegocios.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grupoParceiroNegocios.errors
            return
        }

        try {
            grupoParceiroNegociosService.save(grupoParceiroNegocios)
        } catch (ValidationException e) {
            respond grupoParceiroNegocios.errors
            return
        }

        respond grupoParceiroNegocios, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(GrupoParceiroNegocios grupoParceiroNegocios) {
        if (grupoParceiroNegocios == null) {
            render status: NOT_FOUND
            return
        }
        if (grupoParceiroNegocios.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grupoParceiroNegocios.errors
            return
        }

        try {
            grupoParceiroNegociosService.save(grupoParceiroNegocios)
        } catch (ValidationException e) {
            respond grupoParceiroNegocios.errors
            return
        }

        respond grupoParceiroNegocios, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        grupoParceiroNegociosService.delete(id)

        render status: NO_CONTENT
    }
}
