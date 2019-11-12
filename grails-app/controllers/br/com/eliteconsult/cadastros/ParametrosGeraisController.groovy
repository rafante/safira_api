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
class ParametrosGeraisController {

    ParametrosGeraisService parametrosGeraisService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond parametrosGeraisService.list(params), model:[parametrosGeraisCount: parametrosGeraisService.count()]
    }

    def show(Long id) {
        respond parametrosGeraisService.get(id)
    }

    @Transactional
    def save(ParametrosGerais parametrosGerais) {
        if (parametrosGerais == null) {
            render status: NOT_FOUND
            return
        }
        if (parametrosGerais.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parametrosGerais.errors
            return
        }

        try {
            parametrosGeraisService.save(parametrosGerais)
        } catch (ValidationException e) {
            respond parametrosGerais.errors
            return
        }

        respond parametrosGerais, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ParametrosGerais parametrosGerais) {
        if (parametrosGerais == null) {
            render status: NOT_FOUND
            return
        }
        if (parametrosGerais.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parametrosGerais.errors
            return
        }

        try {
            parametrosGeraisService.save(parametrosGerais)
        } catch (ValidationException e) {
            respond parametrosGerais.errors
            return
        }

        respond parametrosGerais, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        parametrosGeraisService.delete(id)

        render status: NO_CONTENT
    }
}
