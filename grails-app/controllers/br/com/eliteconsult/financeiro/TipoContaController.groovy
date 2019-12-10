package br.com.eliteconsult.financeiro

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class TipoContaController {

    TipoContaService tipoContaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tipoContaService.list(params), model:[tipoContaCount: tipoContaService.count()]
    }

    def show(Long id) {
        respond tipoContaService.get(id)
    }

    @Transactional
    def save(TipoConta tipoConta) {
        if (tipoConta == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoConta.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoConta.errors
            return
        }

        try {
            tipoContaService.save(tipoConta)
        } catch (ValidationException e) {
            respond tipoConta.errors
            return
        }

        respond tipoConta, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TipoConta tipoConta) {
        if (tipoConta == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoConta.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoConta.errors
            return
        }

        try {
            tipoContaService.save(tipoConta)
        } catch (ValidationException e) {
            respond tipoConta.errors
            return
        }

        respond tipoConta, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        tipoContaService.delete(id)

        render status: NO_CONTENT
    }
}
