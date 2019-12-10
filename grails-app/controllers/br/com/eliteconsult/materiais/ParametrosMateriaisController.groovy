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
class ParametrosMateriaisController {

    ParametrosMateriaisService parametrosMateriaisService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond parametrosMateriaisService.list(params), model:[parametrosMateriaisCount: parametrosMateriaisService.count()]
    }

    def show(Long id) {
        respond parametrosMateriaisService.get(id)
    }

    @Transactional
    def save(ParametrosMateriais parametrosMateriais) {
        if (parametrosMateriais == null) {
            render status: NOT_FOUND
            return
        }
        if (parametrosMateriais.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parametrosMateriais.errors
            return
        }

        try {
            parametrosMateriaisService.save(parametrosMateriais)
        } catch (ValidationException e) {
            respond parametrosMateriais.errors
            return
        }

        respond parametrosMateriais, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ParametrosMateriais parametrosMateriais) {
        if (parametrosMateriais == null) {
            render status: NOT_FOUND
            return
        }
        if (parametrosMateriais.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parametrosMateriais.errors
            return
        }

        try {
            parametrosMateriaisService.save(parametrosMateriais)
        } catch (ValidationException e) {
            respond parametrosMateriais.errors
            return
        }

        respond parametrosMateriais, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        parametrosMateriaisService.delete(id)

        render status: NO_CONTENT
    }
}
