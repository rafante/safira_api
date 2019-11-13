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
class TipoDocumentoController {

    TipoDocumentoService tipoDocumentoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tipoDocumentoService.list(params), model:[tipoDocumentoCount: tipoDocumentoService.count()]
    }

    def show(Long id) {
        respond tipoDocumentoService.get(id)
    }

    @Transactional
    def save(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoDocumento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDocumento.errors
            return
        }

        try {
            tipoDocumentoService.save(tipoDocumento)
        } catch (ValidationException e) {
            respond tipoDocumento.errors
            return
        }

        respond tipoDocumento, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoDocumento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDocumento.errors
            return
        }

        try {
            tipoDocumentoService.save(tipoDocumento)
        } catch (ValidationException e) {
            respond tipoDocumento.errors
            return
        }

        respond tipoDocumento, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        tipoDocumentoService.delete(id)

        render status: NO_CONTENT
    }
}
