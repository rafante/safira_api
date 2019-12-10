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
class RecipienteController {

    RecipienteService recipienteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond recipienteService.list(params), model:[recipienteCount: recipienteService.count()]
    }

    def show(Long id) {
        respond recipienteService.get(id)
    }

    @Transactional
    def save(Recipiente recipiente) {
        if (recipiente == null) {
            render status: NOT_FOUND
            return
        }
        if (recipiente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond recipiente.errors
            return
        }

        try {
            recipienteService.save(recipiente)
        } catch (ValidationException e) {
            respond recipiente.errors
            return
        }

        respond recipiente, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Recipiente recipiente) {
        if (recipiente == null) {
            render status: NOT_FOUND
            return
        }
        if (recipiente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond recipiente.errors
            return
        }

        try {
            recipienteService.save(recipiente)
        } catch (ValidationException e) {
            respond recipiente.errors
            return
        }

        respond recipiente, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        recipienteService.delete(id)

        render status: NO_CONTENT
    }
}
