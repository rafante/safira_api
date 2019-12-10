package br.com.eliteconsult.config

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PeriodicidadeController {

    PeriodicidadeService periodicidadeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond periodicidadeService.list(params), model:[periodicidadeCount: periodicidadeService.count()]
    }

    def show(Long id) {
        respond periodicidadeService.get(id)
    }

    @Transactional
    def save(Periodicidade periodicidade) {
        if (periodicidade == null) {
            render status: NOT_FOUND
            return
        }
        if (periodicidade.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond periodicidade.errors
            return
        }

        try {
            periodicidadeService.save(periodicidade)
        } catch (ValidationException e) {
            respond periodicidade.errors
            return
        }

        respond periodicidade, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Periodicidade periodicidade) {
        if (periodicidade == null) {
            render status: NOT_FOUND
            return
        }
        if (periodicidade.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond periodicidade.errors
            return
        }

        try {
            periodicidadeService.save(periodicidade)
        } catch (ValidationException e) {
            respond periodicidade.errors
            return
        }

        respond periodicidade, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        periodicidadeService.delete(id)

        render status: NO_CONTENT
    }
}
