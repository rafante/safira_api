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
class EstadoController {

    EstadoService estadoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond estadoService.list(params), model:[estadoCount: estadoService.count()]
    }

    def show(Long id) {
        respond estadoService.get(id)
    }

    @Transactional
    def save(Estado estado) {
        if (estado == null) {
            render status: NOT_FOUND
            return
        }
        if (estado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond estado.errors
            return
        }

        try {
            estadoService.save(estado)
        } catch (ValidationException e) {
            respond estado.errors
            return
        }

        respond estado, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Estado estado) {
        if (estado == null) {
            render status: NOT_FOUND
            return
        }
        if (estado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond estado.errors
            return
        }

        try {
            estadoService.save(estado)
        } catch (ValidationException e) {
            respond estado.errors
            return
        }

        respond estado, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        estadoService.delete(id)

        render status: NO_CONTENT
    }
}
