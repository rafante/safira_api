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
class ParceiroNegociosController {

    ParceiroNegociosService parceiroNegociosService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond parceiroNegociosService.list(params), model:[parceiroNegociosCount: parceiroNegociosService.count()]
    }

    def show(Long id) {
        respond parceiroNegociosService.get(id)
    }

    @Transactional
    def save(ParceiroNegocios parceiroNegocios) {
        if (parceiroNegocios == null) {
            render status: NOT_FOUND
            return
        }
        if (parceiroNegocios.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parceiroNegocios.errors
            return
        }

        try {
            parceiroNegociosService.save(parceiroNegocios)
        } catch (ValidationException e) {
            respond parceiroNegocios.errors
            return
        }

        respond parceiroNegocios, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ParceiroNegocios parceiroNegocios) {
        if (parceiroNegocios == null) {
            render status: NOT_FOUND
            return
        }
        if (parceiroNegocios.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parceiroNegocios.errors
            return
        }

        try {
            parceiroNegociosService.save(parceiroNegocios)
        } catch (ValidationException e) {
            respond parceiroNegocios.errors
            return
        }

        respond parceiroNegocios, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        parceiroNegociosService.delete(id)

        render status: NO_CONTENT
    }
}
