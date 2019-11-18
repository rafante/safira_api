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
class UnidadeMedidaController {

    UnidadeMedidaService unidadeMedidaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond unidadeMedidaService.list(params), model:[unidadeMedidaCount: unidadeMedidaService.count()]
    }

    def show(Long id) {
        respond unidadeMedidaService.get(id)
    }

    @Transactional
    def save(UnidadeMedida unidadeMedida) {
        if (unidadeMedida == null) {
            render status: NOT_FOUND
            return
        }
        if (unidadeMedida.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond unidadeMedida.errors
            return
        }

        try {
            unidadeMedidaService.save(unidadeMedida)
        } catch (ValidationException e) {
            respond unidadeMedida.errors
            return
        }

        respond unidadeMedida, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(UnidadeMedida unidadeMedida) {
        if (unidadeMedida == null) {
            render status: NOT_FOUND
            return
        }
        if (unidadeMedida.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond unidadeMedida.errors
            return
        }

        try {
            unidadeMedidaService.save(unidadeMedida)
        } catch (ValidationException e) {
            respond unidadeMedida.errors
            return
        }

        respond unidadeMedida, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        unidadeMedidaService.delete(id)

        render status: NO_CONTENT
    }
}
