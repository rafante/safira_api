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
class PaisController {

    PaisService paisService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond paisService.list(params), model:[paisCount: paisService.count()]
    }

    def show(Long id) {
        respond paisService.get(id)
    }

    @Transactional
    def save(Pais pais) {
        if (pais == null) {
            render status: NOT_FOUND
            return
        }
        if (pais.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pais.errors
            return
        }

        try {
            paisService.save(pais)
        } catch (ValidationException e) {
            respond pais.errors
            return
        }

        respond pais, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Pais pais) {
        if (pais == null) {
            render status: NOT_FOUND
            return
        }
        if (pais.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pais.errors
            return
        }

        try {
            paisService.save(pais)
        } catch (ValidationException e) {
            respond pais.errors
            return
        }

        respond pais, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        paisService.delete(id)

        render status: NO_CONTENT
    }
}
